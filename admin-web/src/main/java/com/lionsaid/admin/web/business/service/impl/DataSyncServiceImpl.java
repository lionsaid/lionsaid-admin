package com.lionsaid.admin.web.business.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.google.common.collect.Maps;
import com.lionsaid.admin.web.business.model.po.DataSyncDataSource;
import com.lionsaid.admin.web.business.model.po.DataSyncJob;
import com.lionsaid.admin.web.business.model.po.DataSyncJobFilter;
import com.lionsaid.admin.web.business.model.po.DataSyncLog;
import com.lionsaid.admin.web.business.repository.DataSyncDataSourceRepository;
import com.lionsaid.admin.web.business.repository.DataSyncJobFilterRepository;
import com.lionsaid.admin.web.business.repository.DataSyncJobRepository;
import com.lionsaid.admin.web.business.repository.DataSyncLogRepository;
import com.lionsaid.admin.web.business.service.DataSyncService;
import com.lionsaid.admin.web.datasync.DataSourceUtils;
import com.lionsaid.admin.web.datasync.DataSyncResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
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
        log.error(" dataSyncJobRepository.findById(id) => {}", optional);
        JSONArray failInfo = new JSONArray();
        AtomicReference<Long> success = new AtomicReference<>(0L);
        DataSourceUtils sourceSourceUtils = null;
        if (optional.isPresent()) {
            try {
                DataSyncJob dataSyncJob = optional.get();
                Map<String, List<DataSyncJobFilter>> filter = getFilter(dataSyncJob.getId());
                DataSyncDataSource source = dataSyncDataSourceRepository.findById(dataSyncJob.getSource()).get();
                DataSyncDataSource target = dataSyncDataSourceRepository.findById(dataSyncJob.getTarget()).get();
                sourceSourceUtils = new DataSourceUtils(source, target, dataSyncJob, filter, failInfo);
                DataSyncResult dataSyncResult = DataSyncResult.builder().build();
                do {
                    dataSyncResult = sourceSourceUtils.queryForStream(dataSyncResult);
                    DataSourceUtils finalSourceSourceUtils = sourceSourceUtils;
                    dataSyncResult.getResult().forEach(result -> {
                        if (finalSourceSourceUtils.exist(result)) {
                            if (finalSourceSourceUtils.update(result) != -1) {
                                success.set(success.get() + 1);
                            }
                        } else {
                            if (finalSourceSourceUtils.insert(result) != -1) {
                                success.set(success.get() + 1);
                            }
                        }
                    });
                    dataSyncResult.setPage(dataSyncResult.getPage() + 1);
                } while (dataSyncResult.getResult().count() != 0);
                sourceSourceUtils.close();
            } catch (Exception e) {
                if (ObjectUtils.anyNotNull(sourceSourceUtils)) {
                    sourceSourceUtils.close();
                }
                failInfo.add(e.getMessage());
            }
        } else {
            failInfo.add(id + "被删除或不存在");
        }
        dataSyncLog.setSuccess(success.get());
        dataSyncLog.setFail(Long.valueOf(failInfo.size()));
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
