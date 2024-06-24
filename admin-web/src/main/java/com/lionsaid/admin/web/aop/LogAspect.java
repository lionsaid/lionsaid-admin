package com.lionsaid.admin.web.aop;

import com.lionsaid.admin.web.business.model.po.SysLog;
import com.lionsaid.admin.web.business.service.LogService;
import com.lionsaid.admin.web.utils.LionSaidIdGenerator;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

@Aspect
@Component
@AllArgsConstructor
@Order(2)
public class LogAspect {
    private final LogService logService;

    @Before("@annotation(sysLog)")
    public void logRequest(JoinPoint joinPoint, com.lionsaid.admin.web.annotation.SysLog sysLog) {
        SysLog log = SysLog.builder().description(sysLog.value()).id(LionSaidIdGenerator.snowflakeId())
                .expiredDateTime(LocalDateTime.now().plusDays(sysLog.expired())).build();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        log.setRequestId(request.getRequestId());
        log.setPath(request.getRequestURI());
        log.setMethod(request.getMethod());
        for (Object args : joinPoint.getArgs()) {
            if (!(args instanceof ServletRequest)) {
                log.setParam(log.getParam() + args.toString());
            }
        }
        try {
            logService.saveAndFlush(log);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
