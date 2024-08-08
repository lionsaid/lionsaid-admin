package com.lionsaid.admin.web.business.service.impl;

import com.lionsaid.admin.web.business.model.po.SysLog;
import com.lionsaid.admin.web.business.repository.LogRepository;
import com.lionsaid.admin.web.business.service.LogService;
import com.lionsaid.admin.web.common.IServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LogServiceImpl extends IServiceImpl<SysLog, String, LogRepository> implements LogService {

    @Override
    public void updateExecutionSecondsById(Long executionSeconds, String id) {
        repository.updateExecutionSecondsById(executionSeconds, id);
    }
}
