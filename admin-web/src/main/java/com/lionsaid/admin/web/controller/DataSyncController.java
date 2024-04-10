package com.lionsaid.admin.web.controller;

import com.lionsaid.admin.web.response.ResponseResult;
import com.lionsaid.admin.web.service.DataSyncService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sunwei
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/DataSync")
public class DataSyncController {
    private final DataSyncService logService;


    /**
     * @param id
     * @return
     */
    // @PreAuthorize("hasAnyAuthority('log_get','administration')")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseResult> get(HttpServletRequest request, @PathVariable Long id) {
        log.info("get {}", id);
        logService.dataSync(id);
        return ResponseEntity.ok(ResponseResult.success(""));
    }


}