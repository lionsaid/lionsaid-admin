package com.lionsaid.admin.web.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.google.common.collect.Maps;
import com.lionsaid.admin.web.model.po.DataSyncDataSource;
import com.lionsaid.admin.web.model.po.DataSyncJob;
import com.lionsaid.admin.web.model.po.DataSyncJobFilter;
import com.lionsaid.admin.web.model.po.DataSyncLog;
import com.lionsaid.admin.web.repository.DataSyncDataSourceRepository;
import com.lionsaid.admin.web.repository.DataSyncJobFilterRepository;
import com.lionsaid.admin.web.repository.DataSyncJobRepository;
import com.lionsaid.admin.web.repository.DataSyncLogRepository;
import com.lionsaid.admin.web.service.DataSyncService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
@AllArgsConstructor
public class DataSyncServiceImpl implements DataSyncService {

    private final DataSyncJobRepository dataSyncJobRepository;
    private final DataSyncLogRepository dataSyncLogRepository;
    private final DataSyncJobFilterRepository dataSyncJobFilterRepository;
    private final DataSyncDataSourceRepository dataSyncDataSourceRepository;

    @Override
    public void dataSync(Long id) {
        long start = System.currentTimeMillis();
        DataSyncLog dataSyncLog = DataSyncLog.builder().startDateTime(LocalDateTime.now()).jobId(id).build();
        Optional<DataSyncJob> optional = dataSyncJobRepository.findById(id);
        log.error(" dataSyncJobRepository.findById(id) => {}",optional);
        JSONArray failInfo = new JSONArray();
        AtomicReference<Long> success = new AtomicReference<>(0L);
        AtomicReference<Long> fail = new AtomicReference<>(0L);
        if (optional.isPresent()) {
            try {
                DataSyncJob dataSyncJob = optional.get();
                Map<String, List<DataSyncJobFilter>> filter = getFilter(dataSyncJob.getId());
                DataSyncDataSource source = dataSyncDataSourceRepository.findById(dataSyncJob.getSource()).get();
                DataSyncDataSource target = dataSyncDataSourceRepository.findById(dataSyncJob.getTarget()).get();
                DataSourceUtils sourceSourceUtils = new DataSourceUtils(source, target, dataSyncJob, dataSyncLog, filter, failInfo);
                sourceSourceUtils.queryForStream().forEach(result -> {
                    if (sourceSourceUtils.exist(result)) {
                        sourceSourceUtils.update(result);
                    } else {
                        sourceSourceUtils.insert(result);
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

    private Map<String, List<DataSyncJobFilter>> getFilter(Long jobId) {
        Map<String, List<DataSyncJobFilter>> map = Maps.newConcurrentMap();
        dataSyncJobFilterRepository.findByJobId(jobId).forEach(o -> map.computeIfAbsent(o.getFieldName(), k -> new ArrayList<>()).add(o));
        return map;
    }


}
