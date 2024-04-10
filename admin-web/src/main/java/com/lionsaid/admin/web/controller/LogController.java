package com.lionsaid.admin.web.controller;

import com.lionsaid.admin.web.response.ResponseResult;
import com.lionsaid.admin.web.service.LogService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import java.util.UUID;

/**
 * @author sunwei
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/log")
public class LogController {
    private final LogService logService;


    /**
     * @param id
     * @return
     */
   // @PreAuthorize("hasAnyAuthority('log_get','administration')")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseResult> get(ServerWebExchange exchange, @PathVariable UUID id) {
        log.info("get {}", id);
        return ResponseEntity.ok(ResponseResult.success(logService.getReferenceById(id)));
    }

//
//    /**
//     * @param findInfo
//     * @param entity
//     * @return
//     */
//    @PreAuthorize("hasAnyAuthority('log_get','administration')")
//    @GetMapping("/findAll")
//    public Mono findAll(ServerWebExchange exchange, FindInfo findInfo, Log entity) {
//        return ResponseEntity.ok(ResponseResult.success(logService.getReferenceById(id)));
//    }
//
//    @SysLog(value = "用户获取本人日志")
//    @GetMapping("/getUserLog")
//    public Mono<ApiResponse> userLogService(ServerWebExchange exchange, @RequestHeader String userId) {
//        return null;
//    }


}