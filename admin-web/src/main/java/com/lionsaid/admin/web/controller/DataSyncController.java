package com.lionsaid.admin.web.controller;

import com.lionsaid.admin.web.service.DataSyncService;
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
    @GetMapping("startJob")
    public ResponseEntity<String> get(HttpServletRequest request, Long id) {
        log.error("startJob {}", id);
        dataSyncService.dataSync(id);
        return ResponseEntity.ok("ok");
    }

}