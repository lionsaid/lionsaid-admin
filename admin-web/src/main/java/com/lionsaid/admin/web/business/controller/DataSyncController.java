package com.lionsaid.admin.web.business.controller;

import com.lionsaid.admin.web.annotation.LionSaidAuth;
import com.lionsaid.admin.web.annotation.SysLog;
import com.lionsaid.admin.web.business.service.DataSyncService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/dataSync")
public class DataSyncController {
    private final DataSyncService dataSyncService;

    /**
     * @param id
     * @return
     */
    @SysLog(value = "数据同步 开始执行任务")
    @GetMapping("startJob")
    @LionSaidAuth(value = {"admin", "dataSync_startJob"})
    public ResponseEntity<String> startJob(HttpServletRequest request, Long id) {
        log.error("startJob {}", id);
        dataSyncService.dataSync(id);
        return ResponseEntity.ok("ok");
    }

}