package com.lionsaid.admin.web.business.service.impl;

import com.lionsaid.admin.web.business.model.po.ScheduledTaskLog;
import com.lionsaid.admin.web.business.repository.ScheduledTaskLogRepository;
import com.lionsaid.admin.web.business.service.ScheduledTaskLogService;
import com.lionsaid.admin.web.common.IServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class ScheduledTaskLogServiceImpl extends IServiceImpl<ScheduledTaskLog, String, ScheduledTaskLogRepository> implements ScheduledTaskLogService {

}
