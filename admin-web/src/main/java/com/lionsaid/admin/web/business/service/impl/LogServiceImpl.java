package com.lionsaid.admin.web.business.service.impl;

import com.lionsaid.admin.web.common.IServiceImpl;
import com.lionsaid.admin.web.business.model.po.SysLog;
import com.lionsaid.admin.web.business.repository.LogRepository;
import com.lionsaid.admin.web.business.service.LogService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class LogServiceImpl extends IServiceImpl<SysLog, UUID, LogRepository> implements LogService {

}
