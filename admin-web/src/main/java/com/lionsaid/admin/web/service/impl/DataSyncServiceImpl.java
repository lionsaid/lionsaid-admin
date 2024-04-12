package com.lionsaid.admin.web.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import com.lionsaid.admin.web.model.po.DataSyncDataSource;
import com.lionsaid.admin.web.model.po.DataSyncJob;
import com.lionsaid.admin.web.model.po.DataSyncLog;
import com.lionsaid.admin.web.repository.DataSyncDataSourceRepository;
import com.lionsaid.admin.web.repository.DataSyncJobRepository;
import com.lionsaid.admin.web.repository.DataSyncLogRepository;
import com.lionsaid.admin.web.service.DataSyncService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSetMetaData;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class DataSyncServiceImpl implements DataSyncService {
    private final DataSyncJobRepository dataSyncJobRepository;
    private final DataSyncLogRepository dataSyncLogRepository;
    private final DataSyncDataSourceRepository dataSyncDataSourceRepository;

    @Override
    public void dataSync(Long id) {
        long start = System.currentTimeMillis();
        DataSyncLog dataSyncLog = DataSyncLog.builder().startDateTime(LocalDateTime.now()).jobId(id).build();
        Optional<DataSyncJob> optional = dataSyncJobRepository.findById(id);
        JSONArray failInfo = new JSONArray();
        AtomicReference<Long> success = new AtomicReference<>(0L);
        AtomicReference<Long> fail = new AtomicReference<>(0L);
        if (optional.isPresent()) {
            try {
                DataSyncJob dataSyncJob = optional.get();
                DataSyncDataSource source = dataSyncDataSourceRepository.findById(dataSyncJob.getSource()).get();
                DataSyncDataSource target = dataSyncDataSourceRepository.findById(dataSyncJob.getTarget()).get();
                JdbcTemplate sourcejdbcTemplate = new JdbcTemplate(DataSourceBuilder.create().url(source.getUrl()).password(source.getPassword()).username(source.getUsername()).driverClassName(source.getDriverClassName()).build());
                JdbcTemplate targetjdbcTemplate = new JdbcTemplate(DataSourceBuilder.create().url(target.getUrl()).password(target.getPassword()).username(target.getUsername()).driverClassName(target.getDriverClassName()).build());
                // 执行源数据源的查询，并遍历结果集
                sourcejdbcTemplate.queryForStream(dataSyncJob.getSourceSql(), (rs, rowNum) -> {
                    JSONObject jsonObject = new JSONObject();
                    ResultSetMetaData resultSetMetaData = rs.getMetaData();
                    for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                        jsonObject.put(resultSetMetaData.getColumnName(i), rs.getObject(i));
                    }
                    return jsonObject;
                }).forEach(result -> {
                    String sql = "";
                    Object[] args = new Object[0];
                    try {
                        ArrayList<@Nullable String> ids = Lists.newArrayList();
                        Arrays.stream(dataSyncJob.getTargetId().split(",")).forEach(o -> {
                            ids.add(result.getString(o));
                        });
                        Long count = 0L;
                        if (StringUtils.isNotEmpty(dataSyncJob.getTargetId())) {
                            count = targetjdbcTemplate.queryForObject("SELECT COUNT(1) FROM  " + dataSyncJob.getTargetTable() + " WHERE " + Arrays.stream(dataSyncJob.getTargetId().split(",")).map(o -> "`" + o + "`=?").collect(Collectors.joining("  and ")), Long.class, ids.toArray());
                        }
                        if (count > 0) {
                            sql = "update  " + dataSyncJob.getTargetTable() + " set " + result.keySet().stream().map(o -> "`" + o + "`=? ").collect(Collectors.joining(",")) + " where "
                                    + Arrays.stream(dataSyncJob.getTargetId().split(",")).map(o -> "`" + o + "`=?").collect(Collectors.joining("  and "));
                            ArrayList<@Nullable Object> arrayList = Lists.newArrayList();
                            arrayList.addAll(result.values());
                            arrayList.addAll(ids);
                            args = arrayList.toArray();
                            // 执行更新操作...
                            targetjdbcTemplate.update(sql, args);
                        } else {
                            sql = "INSERT INTO " + dataSyncJob.getTargetTable() + " (" + result.keySet().stream().map(o -> "`" + o + "`").collect(Collectors.joining(",")) + ")\n" +
                                    "VALUES (" + result.keySet().stream().map(o -> "?").collect(Collectors.joining(",")) + ");\n";
                            args = result.values().toArray();
                            // 执行插入操作...
                            targetjdbcTemplate.update(sql, args);
                        }
                        success.getAndSet(success.get() + 1);
                        // 记录日志
                    } catch (Exception e) {
                        fail.getAndSet(fail.get() + 1);
                        // 发生异常时记录错误日志
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("sql", sql);
                        jsonObject.put("args", args);
                        jsonObject.put("message", e.getMessage());
                        failInfo.add(jsonObject);
                    }
                });
            } catch (Exception e) {
                failInfo.add(e.getMessage());
            }
        } else {
            failInfo.add(id + "被删除或不存在");
        }
        dataSyncLog.setSuccess(success.get());
        dataSyncLog.setFail(fail.get());
        dataSyncLog.setFailInfo(failInfo.toJSONString());
        dataSyncLog.setEndDateTime(LocalDateTime.now());
        dataSyncLog.setExecutionTime(System.currentTimeMillis() - start);
        dataSyncLogRepository.saveAndFlush(dataSyncLog);
    }
}
