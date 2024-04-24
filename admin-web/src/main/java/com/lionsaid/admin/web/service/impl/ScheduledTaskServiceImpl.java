package com.lionsaid.admin.web.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lionsaid.admin.web.common.IServiceImpl;
import com.lionsaid.admin.web.model.po.ScheduledTask;
import com.lionsaid.admin.web.model.po.ScheduledTaskLog;
import com.lionsaid.admin.web.repository.ScheduledTaskRepository;
import com.lionsaid.admin.web.service.ScheduledTaskLogService;
import com.lionsaid.admin.web.service.ScheduledTaskService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;

@Slf4j
@Service
@Component
@AllArgsConstructor
public class ScheduledTaskServiceImpl extends IServiceImpl<ScheduledTask, String, ScheduledTaskRepository> implements ScheduledTaskService {

    private final TaskScheduler taskScheduler;
    private final StringRedisTemplate redisTemplate;
    private final RedissonClient redissonClient;
    private final ScheduledTaskLogService scheduledTaskLogService;
    private static Map<String, ScheduledFuture<?>> scheduledTasks = Maps.newConcurrentMap();

    @Scheduled(fixedRate = 60000) // 每分钟执行一次
    public void doSomething() {
        repository.findByTaskStatus(1).forEach(this::startOrSkipTask);
        log.info("执行任务逻辑...");
    }

    private void startOrSkipTask(ScheduledTask scheduledTask) {
        if (!scheduledTasks.containsKey(scheduledTask.getId())) {
            startTask(scheduledTask.getId());
            redisTemplate.opsForHash().put("scheduledTask", scheduledTask.getId(), JSON.toJSONString(scheduledTask));
        }
    }

    @Override
    public void removeTask(String taskId) {
        ScheduledFuture<?> scheduledFuture = scheduledTasks.remove(taskId);
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
            redisTemplate.opsForHash().delete("scheduledTask", taskId);
            syncTaskChangeToRedis(taskId, "delete");
        } else {
            throw new IllegalArgumentException("Task with ID " + taskId + " not found");
        }
        repository.deleteById(taskId);
    }

    @Override
    public List<JSONObject> getRunTask() {
        List<JSONObject> list = Lists.newArrayList();
        scheduledTasks.forEach((key, value) -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", key);
            jsonObject.put("taskInfo", value);
            list.add(jsonObject);
        });
        return list;
    }

    @Override
    public void stopTask(String taskId) {
        ScheduledFuture<?> scheduledFuture = scheduledTasks.remove(taskId);
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
            syncTaskChangeToRedis(taskId, "stop");
            repository.updateTaskStatusById(0, taskId);
        } else {
            throw new IllegalArgumentException("Task with ID " + taskId + " not found");
        }
    }

    @Override
    public void startTask(String taskId) {
        if (!scheduledTasks.containsKey(taskId)) {
            Optional<ScheduledTask> optional = repository.findById(taskId);
            optional.ifPresentOrElse(scheduledTask -> {
                Runnable runnable = () -> {
                    RLock lock = null;
                    try {
                        lock = redissonClient.getLock("lock_scheduled" + taskId + scheduledTask.getCreatedDate());
                        if (lock.tryLock()) {
                            log.info("Acquired lock for task {}", scheduledTask.getId());
                            executeTask(scheduledTask);
                        } else {
                            log.warn("Failed to acquire lock for task {}", scheduledTask.getId());
                        }
                    } finally {
                        if (null != lock && lock.isHeldByCurrentThread()) {
                            lock.unlock();
                            log.debug("Released lock");
                        }
                    }
                };
                // Schedule and store task information outside the lock
                Trigger trigger = new CronTrigger(scheduledTask.getCron());
                ScheduledFuture<?> scheduledFuture = taskScheduler.schedule(runnable, trigger);
                scheduledTasks.put(scheduledTask.getId(), scheduledFuture);
                redisTemplate.opsForHash().put("scheduledTask", scheduledTask.getId().toString(), JSON.toJSONString(scheduledTask));
                syncTaskChangeToRedis(taskId, "start");
            }, () -> log.error("Task with ID {} not found", taskId));
        }
    }


    private void executeTask(ScheduledTask scheduledTask) {
        long start = System.currentTimeMillis();
        ScheduledTaskLog scheduledTaskLog = ScheduledTaskLog.builder().id(UUID.randomUUID().toString()).taskId(scheduledTask.getId()).startDateTime(LocalDateTime.now()).status(0).build();
        scheduledTaskLogService.saveAndFlush(scheduledTaskLog);
        scheduledTaskLog.setStatus(2);
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            // 执行任务逻辑
            switch (scheduledTask.getTaskType().toLowerCase()) {
                case "curl":
                    try {
                        Response response = executeCurl(scheduledTask.getTaskInfo());
                        if (response.isSuccessful()) {
                            scheduledTaskLog.setStatus(1);
                        } else {
                            scheduledTaskLog.setExecuteInfo(response.toString());
                        }
                    } catch (Exception e) {
                        scheduledTaskLog.setExecuteInfo(e.getMessage());
                        log.error("Error executing task logic: {}", e.getMessage());
                    }
                    break;
                default:
                    scheduledTaskLog.setExecuteInfo("不被支持的任务类型");

            }
        });
        try {
            //默认3分钟
            future.get(ObjectUtils.anyNull(scheduledTask.getTimeout()) ? 3 : scheduledTask.getTimeout(), TimeUnit.MINUTES);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            scheduledTaskLog.setExecuteInfo(e.getMessage());
            throw new RuntimeException(e);
        }
        scheduledTaskLog.setEndDateTime(LocalDateTime.now());
        scheduledTaskLog.setExecutionTime(System.currentTimeMillis() - start);
        scheduledTaskLogService.saveAndFlush(scheduledTaskLog);
    }

    private void syncTaskChangeToRedis(String taskId, String method) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("taskId", taskId);
        jsonObject.put("method", method);
        redisTemplate.convertAndSend("scheduledChannel", jsonObject.toString());
    }

    @Override
    public void updateTask(ScheduledTask scheduledTask) {
        repository.saveAndFlush(scheduledTask);
        stopTask(scheduledTask.getId());
        startTask(scheduledTask.getId());
    }

    @SneakyThrows
    public static Response executeCurl(String curlCommand) {
        // 解析Curl命令
        String[] tokens = curlCommand.split("\\s+");
        String url = null;
        HttpMethod method = HttpMethod.GET;
        Map<String, String> headers = new HashMap<>();
        JSONObject requestBodyMap = new JSONObject();
        int timeout = 0;
        String requestBody = null;

        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].equals("--request") || tokens[i].equals("-X")) {
                method = HttpMethod.valueOf(tokens[i + 1].toUpperCase());
            } else if (tokens[i].equals("--header") || tokens[i].equals("-H")) {
                String[] headerTokens = tokens[i + 1].split(":");
                headers.put(headerTokens[0].trim(), headerTokens[1].trim());
            } else if (tokens[i].equals("--data-urlencode")) {
                String[] headerTokens = tokens[i + 1].split("=");
                requestBodyMap.put(headerTokens[0].trim(), headerTokens[1].trim());
            } else if (tokens[i].equals("--data")) {
                requestBody = tokens[i + 1];
            } else if (tokens[i].equals("--max-time")) {
                timeout = Integer.parseInt(tokens[i + 1]);
            } else if (tokens[i].startsWith("'http")) {
                url = tokens[i].replaceAll("'", "");
            } else if (tokens[i].startsWith("\"http")) {
                url = tokens[i].replaceAll("\"", "");
            }
        }


        // 检查URL是否为空
        if (url == null || url.isEmpty()) {
            throw new IllegalArgumentException("URL cannot be null or empty");
        }

        // 创建OkHttpClient对象，并设置超时
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        if (timeout > 0) {
            clientBuilder.connectTimeout(timeout, TimeUnit.SECONDS);
            clientBuilder.readTimeout(timeout, TimeUnit.SECONDS);
            clientBuilder.writeTimeout(timeout, TimeUnit.SECONDS);
        }
        OkHttpClient client = clientBuilder.build();
        // 创建Request.Builder对象
        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .method(method.name(), ObjectUtils.allNull(requestBody) ? null : RequestBody.create(MediaType.parse(headers.getOrDefault("Content-Type", "application/json")), StringUtils.isEmpty(requestBody) ? requestBodyMap.toJSONString() : requestBody));

        // 添加请求头部
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            requestBuilder.addHeader(entry.getKey(), entry.getValue());
        }
        return client.newCall(requestBuilder.build()).execute();
    }


}
