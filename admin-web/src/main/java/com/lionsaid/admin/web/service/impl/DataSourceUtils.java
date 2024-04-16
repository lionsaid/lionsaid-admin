package com.lionsaid.admin.web.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import com.lionsaid.admin.web.model.po.DataSyncDataSource;
import com.lionsaid.admin.web.model.po.DataSyncJob;
import com.lionsaid.admin.web.model.po.DataSyncJobFilter;
import com.lionsaid.admin.web.model.po.DataSyncLog;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataSourceUtils {
    private final JdbcTemplate sourceJdbcTemplate;
    private final JdbcTemplate targetJdbcTemplate;
    private final Map<String, List<DataSyncJobFilter>> filter;
    private final DataSyncDataSource source;
    private final DataSyncDataSource target;
    private final DataSyncJob dataSyncJob;
    private final JSONArray failInfo;

    public DataSourceUtils(DataSyncDataSource source, DataSyncDataSource target, DataSyncJob dataSyncJob, DataSyncLog dataSyncLog, Map<String, List<DataSyncJobFilter>> filter, JSONArray failInfo) {
        this.source = source;
        this.target = target;
        this.dataSyncJob = dataSyncJob;
        this.failInfo = failInfo;
        this.filter = filter;

        // 初始化源数据源的 JdbcTemplate
        sourceJdbcTemplate = new JdbcTemplate(DataSourceBuilder.create()
                .url(source.getUrl())
                .username(source.getUsername())
                .password(source.getPassword())
                .driverClassName(source.getDriverClassName())
                .build());

        // 初始化目标数据源的 JdbcTemplate
        targetJdbcTemplate = new JdbcTemplate(DataSourceBuilder.create()
                .url(target.getUrl())
                .username(target.getUsername())
                .password(target.getPassword())
                .driverClassName(target.getDriverClassName())
                .build());
    }

    public Stream<JSONObject> queryForStream() {
        return sourceJdbcTemplate.queryForStream(dataSyncJob.getSourceSql(), (rs, rowNum) -> {
            JSONObject jsonObject = new JSONObject();
            ResultSetMetaData resultSetMetaData = rs.getMetaData();
            for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                jsonObject.put(resultSetMetaData.getColumnName(i), rs.getObject(i));
            }
            return jsonObject;
        }).map(o -> {
            if (filter.isEmpty()) {
                return o;
            } else {
                try {
                    return mapFilter(o, filter);
                } catch (Exception e) {
                    // 发生异常时记录错误日志
                    JSONObject errorObject = new JSONObject();
                    errorObject.put("message", e.getMessage());
                    errorObject.put("data", o);
                    failInfo.add(errorObject);
                    return new JSONObject();
                }
            }
        }).filter(o -> !o.isEmpty());
    }

    public Boolean exist(JSONObject result) {
        ArrayList<String> ids = new ArrayList<>();
        Arrays.stream(dataSyncJob.getTargetId().split(",")).forEach(o -> ids.add(result.getString(o)));
        Long count = 0L;
        String sql;
        if (!StringUtils.isEmpty(dataSyncJob.getTargetId())) {
            switch (target.getSourceType().toLowerCase()) {
                case "mysql":
                    sql = "SELECT COUNT(1) FROM  " + dataSyncJob.getTargetTable() + " WHERE " + Arrays.stream(dataSyncJob.getTargetId().split(",")).map(o -> "`" + o + "` =?").collect(Collectors.joining("  and "));
                    break;
                case "kingbase8":
                case "informix":
                    sql = "SELECT COUNT(1) FROM  " + dataSyncJob.getTargetTable() + " WHERE " + Arrays.stream(dataSyncJob.getTargetId().split(",")).map(o -> o + "= ?").collect(Collectors.joining("  and "));
                    break;
                case "sql server":
                    sql = "SELECT COUNT(1) FROM  " + dataSyncJob.getTargetTable() + " WHERE " + Arrays.stream(dataSyncJob.getTargetId().split(",")).map(o -> "[" + o + "] =?").collect(Collectors.joining("  and "));
                    break;
                default:
                    throw new RuntimeException("不支持的数据源");
            }
            count = targetJdbcTemplate.queryForObject(sql, Long.class, ids.toArray());
        }
        return count > 0;
    }

    public int update(JSONObject result) {
        String sql;
        Object[] args;
        ArrayList<Object> argsList = new ArrayList<>();
        Arrays.stream(dataSyncJob.getTargetId().split(",")).forEach(o -> argsList.add(result.getString(o)));
        switch (target.getSourceType().toLowerCase()) {
            case "mysql":
                sql = "update " + dataSyncJob.getTargetTable() + " set " + result.keySet().stream().map(o -> "`" + o + "`=? ").collect(Collectors.joining(",")) + " where " + Arrays.stream(dataSyncJob.getTargetId().split(",")).map(o -> "`" + o + "`=?").collect(Collectors.joining("  and "));
                break;
            case "kingbase8":
            case "informix":
            case "sql server":
                sql = "update " + dataSyncJob.getTargetTable() + " set " + result.keySet().stream().map(o -> "[" + o + "] =? ").collect(Collectors.joining(",")) + " where " + Arrays.stream(dataSyncJob.getTargetId().split(",")).map(o -> "[" + o + "] =?").collect(Collectors.joining("  and "));
                break;
            default:
                throw new RuntimeException("不支持的数据源");
        }
        argsList.addAll(result.values());
        args = argsList.toArray();
        try {
            // 执行更新操作...
            return targetJdbcTemplate.update(sql, args);
        } catch (Exception e) {
            // 发生异常时记录错误日志
            JSONObject errorObject = new JSONObject();
            errorObject.put("sql", sql);
            errorObject.put("args", args);
            errorObject.put("message", e.getMessage());
            failInfo.add(errorObject);
            return -1;
        }
    }

    public int insert(JSONObject result) {
        String sql;
        Object[] args;
        switch (target.getSourceType().toLowerCase()) {
            case "mysql":
                sql = "INSERT INTO " + dataSyncJob.getTargetTable() + " (" + result.keySet().stream().map(o -> "`" + o + "`").collect(Collectors.joining(",")) + ")\n" + "VALUES (" + result.keySet().stream().map(o -> "?").collect(Collectors.joining(",")) + ");\n";
                break;
            case "kingbase8":
            case "informix":
            case "sql server":
                sql = "INSERT INTO " + dataSyncJob.getTargetTable() + " (" + result.keySet().stream().collect(Collectors.joining(",")) + ")\n" + "VALUES (" + result.keySet().stream().map(o -> "?").collect(Collectors.joining(",")) + ");\n";
                break;
            default:
                throw new RuntimeException("不支持的数据源");
        }
        args = result.values().toArray();
        try {
            // 执行插入操作...
            return targetJdbcTemplate.update(sql, args);
        } catch (Exception e) {
            // 发生异常时记录错误日志
            JSONObject errorObject = new JSONObject();
            errorObject.put("sql", sql);
            errorObject.put("args", args);
            errorObject.put("message", e.getMessage());
            failInfo.add(errorObject);
            return -1;
        }
    }

    private JSONObject mapFilter(JSONObject jsonObject, Map<String, List<DataSyncJobFilter>> filter) {
        filter.forEach((key, filterList) -> {
            if (jsonObject.containsKey(key)) {
                filterList.forEach(o -> {
                    JSONObject setting = new JSONObject();
                    if (StringUtils.isNotEmpty(o.getSetting())) {
                        setting = JSONObject.parseObject(o.getSetting());
                    }
                    switch (o.getFilterType()) {
                        case "json_encode":
                            jsonObject.put(key, JSON.parse(jsonObject.getString(key)));
                            break;
                        case "url_decode":
                            jsonObject.put(key, URLDecoder.decode(jsonObject.getString(key), setting.containsKey("charset") ? Charset.forName(setting.getString("charset")) : Charset.forName("utf-8")));
                            break;
                        case "url_encode":
                            jsonObject.put(key, URLEncoder.encode(jsonObject.getString(key), setting.containsKey("charset") ? Charset.forName(setting.getString("charset")) : Charset.forName("utf-8")));
                            break;
                        case "replace_all":
                            jsonObject.put(key, jsonObject.getString(key).replaceAll(setting.getString("regex"), setting.getString("replacement")));
                            break;
                        case "trim":
                            jsonObject.put(key, jsonObject.getString(key).trim());
                            break;
                        default:
                            break;

                    }
                });
            }
        });
        return jsonObject;
    }
}
