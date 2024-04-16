package com.lionsaid.admin.web.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lionsaid.admin.web.common.IServiceImpl;
import com.lionsaid.admin.web.model.po.ScheduledTask;
import com.lionsaid.admin.web.repository.ScheduledTaskRepository;
import com.lionsaid.admin.web.service.ScheduledTaskService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@AllArgsConstructor
public class ScheduledTaskServiceImpl extends IServiceImpl<ScheduledTask, Long, ScheduledTaskRepository> implements ScheduledTaskService {

    private TaskScheduler taskScheduler;

    private static Map<Long, ScheduledFuture<?>> scheduledTasks = Maps.newHashMap();


    @Override
    public void removeTask(Long taskId) {
        ScheduledFuture<?> scheduledFuture = scheduledTasks.get(taskId);
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
            scheduledTasks.remove(taskId);
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
            jsonObject.put("delay", value.getDelay(TimeUnit.MINUTES));
            jsonObject.put("cancelled", value.isCancelled());
            jsonObject.put("done", value.isDone());
            list.add(jsonObject);
        });
        return list;
    }

    @Override
    public void stopTask(Long taskId) {
        ScheduledFuture<?> scheduledFuture = scheduledTasks.get(taskId);
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
            scheduledTasks.remove(taskId);
            repository.updateTaskStatusById(0, taskId);
        } else {
            throw new IllegalArgumentException("Task with ID " + taskId + " not found");
        }
    }

    @Override
    public void startTask(Long taskId) {
        ScheduledTask scheduledTask = repository.getReferenceById(taskId);
        if (!scheduledTasks.containsKey(scheduledTask.getId())) {
            JSONObject parsedObject = JSONObject.parseObject(scheduledTask.getTaskInfo());
            Runnable taskRunnable = () -> {
                switch (scheduledTask.getTaskType()) {
                    case "http", "https":
                        OkHttpClient client = new OkHttpClient.Builder()
                                .callTimeout(parsedObject.getInteger("callTimeout"), TimeUnit.MINUTES) // 设置超时时间为 1 天
                                .build();
                        Request request = new Request.Builder().method(parsedObject.getString("method"), RequestBody.create(MediaType.parse("text/plain"), parsedObject.getString("requestBody")))
                                .url(parsedObject.getString("url"))
                                .build();
                        try (Response response = client.newCall(request).execute()) {
                            if (response.code() >= 200 && response.code() <= 300) {

                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    default:
                        break;
                }
                // Execute the task logic
                log.error("{}", scheduledTask);
            };
            Trigger trigger = new CronTrigger(scheduledTask.getCron());
            ScheduledFuture<?> scheduledFuture = taskScheduler.schedule(taskRunnable, trigger);
            scheduledTasks.put(scheduledTask.getId(), scheduledFuture);
        }
    }

    @Override
    public void updateTask(ScheduledTask scheduledTask) {
        repository.saveAndFlush(scheduledTask);
    }
}
