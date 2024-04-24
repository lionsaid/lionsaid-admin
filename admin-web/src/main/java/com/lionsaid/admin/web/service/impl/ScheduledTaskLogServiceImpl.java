package com.lionsaid.admin.web.service.impl;

import com.lionsaid.admin.web.common.IServiceImpl;
import com.lionsaid.admin.web.model.po.ScheduledTaskLog;
import com.lionsaid.admin.web.repository.ScheduledTaskLogRepository;
import com.lionsaid.admin.web.service.ScheduledTaskLogService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class ScheduledTaskLogServiceImpl extends IServiceImpl<ScheduledTaskLog, String, ScheduledTaskLogRepository> implements ScheduledTaskLogService {

}
