package com.lionsaid.admin.web.business.controller;

import com.alibaba.fastjson2.JSONObject;
import com.lionsaid.admin.web.business.model.po.ScheduledTask;
import com.lionsaid.admin.web.business.service.ScheduledTaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author sunwei
 */
@Tag(name = "定时任务")
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "/private/api/scheduled", name = "scheduledManage")
public class ScheduledController {
    private final ScheduledTaskService scheduledTaskService;

    @Operation(description = "新增定时任务", summary = "新增定时任务")
    @PreAuthorize("hasAnyAuthority('scheduledPost','administration','scheduledManage')")
    @PostMapping("")
    public ResponseEntity<String> postTask(@RequestBody ScheduledTask task) {
        scheduledTaskService.saveAndFlush(task);
        return ResponseEntity.ok("Task added successfully");
    }

    @Operation(description = "更新定时任务", summary = "更新定时任务")
    @PreAuthorize("hasAnyAuthority('scheduledPut','administration','scheduledManage')")
    @PutMapping("")
    public ResponseEntity<String> putTask(@RequestBody ScheduledTask task) {
        scheduledTaskService.saveAndFlush(task);
        return ResponseEntity.ok("Task added successfully");
    }

    @Operation(description = "停止定时任务", summary = "停止定时任务")
    @PreAuthorize("hasAnyAuthority('scheduledPut','administration','scheduledManage')")
    @PutMapping("/stopTask/{taskId}")
    public ResponseEntity<String> stopTask(@PathVariable String taskId) {
        scheduledTaskService.stopTask(taskId);
        return ResponseEntity.ok("Task added successfully");
    }

    @Operation(description = "开始定时任务", summary = "开始定时任务")
    @PreAuthorize("hasAnyAuthority('scheduledPut','administration','scheduledManage')")
    @PutMapping("/startTask/{taskId}")
    public ResponseEntity<String> startTask(@PathVariable String taskId) {
        scheduledTaskService.startTask(taskId);
        return ResponseEntity.ok("Task stop successfully");
    }

    @Operation(description = "查询定时任务", summary = "查询定时任务")
    @PreAuthorize("hasAnyAuthority('scheduledGet','administration','scheduledManage')")
    @GetMapping("/getRunTask")
    public ResponseEntity<List<JSONObject>> getRunTask() {
        return ResponseEntity.ok(scheduledTaskService.getRunTask());
    }

    @Operation(description = "删除定时任务", summary = "删除定时任务")
    @PreAuthorize("hasAnyAuthority('scheduledDelete','administration','scheduledManage')")
    @DeleteMapping("/remove/{taskId}")
    public ResponseEntity<String> removeTask(@PathVariable String taskId) {
        scheduledTaskService.removeTask(taskId);
        return ResponseEntity.ok("Task removed successfully");
    }


}