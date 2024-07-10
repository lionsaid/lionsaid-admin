package com.lionsaid.admin.web.business.controller;

import com.lionsaid.admin.web.annotation.SysLog;
import com.lionsaid.admin.web.business.service.DataSyncService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sunwei
 */
@Tag(name = "数据同步")
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "/dataSync", name = "DataSyncManage")
public class DataSyncController {
    private final DataSyncService dataSyncService;

    /**
     * @param id
     * @return
     */
    @Operation(description = "数据同步 开始执行任务", summary = "数据同步 开始执行任务")
    @PreAuthorize("hasAnyAuthority('dataSyncGet','administration','DataSyncManage')")
    @SysLog(value = "数据同步 开始执行任务")
    @GetMapping("startJob")
    public ResponseEntity<String> startJob(Long id) {
        log.error("startJob {}", id);
        dataSyncService.dataSync(id);
        return ResponseEntity.ok("ok");
    }

}