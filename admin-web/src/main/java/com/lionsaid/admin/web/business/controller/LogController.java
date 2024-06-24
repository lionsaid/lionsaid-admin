package com.lionsaid.admin.web.business.controller;

import com.lionsaid.admin.web.annotation.SysLog;
import com.lionsaid.admin.web.business.service.LogService;
import com.lionsaid.admin.web.response.ResponseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sunwei
 */
@Tag(name = "用户日志")
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "/private/log", name = "logManage")
public class LogController {
    private final LogService logService;

    /**
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @SysLog(value = "用户日志 查询日志")
    @Operation(description = "用户日志 查询日志", summary = "用户日志 查询日志")
    @PreAuthorize("hasAnyAuthority('logGet','administration','logManage')")
    public ResponseEntity<ResponseResult> get(@PathVariable String id) {
        log.info("get {}", id);
        return ResponseEntity.ok(ResponseResult.success(logService.getReferenceById(id)));
    }
}