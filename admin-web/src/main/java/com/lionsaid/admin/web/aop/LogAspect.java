package com.lionsaid.admin.web.aop;

import com.lionsaid.admin.web.annotation.SysLog;
import com.lionsaid.admin.web.model.po.Log;
import com.lionsaid.admin.web.service.LogService;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.time.LocalDateTime;

@Aspect
@Component
@AllArgsConstructor
@Order(2)
public class LogAspect {
    private final LogService logService;

    @Before("@annotation(sysLog)")
    public void logRequest(JoinPoint joinPoint, SysLog sysLog) {
        Log log = Log.builder().description(sysLog.value())
                .expiredDateTime(LocalDateTime.now().plusDays(sysLog.expired())).build();
        for (Object args : joinPoint.getArgs()) {
            if (args instanceof ServletRequest) {
                HttpServletRequest request = (HttpServletRequest) args;
                log.setRequestId(request.getRequestId());
                // 在这里可以访问serverWebExchange对象，执行你的逻辑
                // 例如，获取请求信息、响应信息等
                log.setPath(request.getRequestURI());
                log.setMethod(request.getMethod());
                // serverWebExchange.getRequest(), serverWebExchange.getResponse(), ...
            } else {
                log.setParam(args.toString());
            }
        }
        try {
            logService.saveAndFlush(log);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
