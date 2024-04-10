package com.lionsaid.admin.web.service.impl;

import com.lionsaid.admin.web.common.IServiceImpl;
import com.lionsaid.admin.web.model.po.Log;
import com.lionsaid.admin.web.repository.LogRepository;
import com.lionsaid.admin.web.service.LogService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class LogServiceImpl extends IServiceImpl<Log, UUID, LogRepository> implements LogService {

}
