package com.lionsaid.admin.web.business.controller;

import com.alibaba.fastjson2.JSONObject;
import com.lionsaid.admin.web.business.model.po.ScheduledTask;
import com.lionsaid.admin.web.business.service.ScheduledTaskService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author sunwei
 */
@Tag(name = "定时任务")
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/scheduled")
public class ScheduledController {
    private final ScheduledTaskService scheduledTaskService;


    @PostMapping("/post")
    public ResponseEntity<String> postTask(@RequestBody ScheduledTask task) {
        scheduledTaskService.saveAndFlush(task);
        return ResponseEntity.ok("Task added successfully");
    }

    @PutMapping("/put")
    public ResponseEntity<String> putTask(@RequestBody ScheduledTask task) {
        scheduledTaskService.saveAndFlush(task);
        return ResponseEntity.ok("Task added successfully");
    }

    @GetMapping("/stopTask/{taskId}")
    public ResponseEntity<String> stopTask(@PathVariable String taskId) {
        scheduledTaskService.stopTask(taskId);
        return ResponseEntity.ok("Task added successfully");
    }

    @GetMapping("/startTask/{taskId}")
    public ResponseEntity<String> startTask(@PathVariable String taskId) {scheduledTaskService.startTask(taskId);
        return ResponseEntity.ok("Task stop successfully");
    }

    @GetMapping("/getRunTask")
    public ResponseEntity<List<JSONObject>> getRunTask() {
        return ResponseEntity.ok(scheduledTaskService.getRunTask());
    }

    @DeleteMapping("/remove/{taskId}")
    public ResponseEntity<String> removeTask(@PathVariable String taskId) {
        scheduledTaskService.removeTask(taskId);
        return ResponseEntity.ok("Task removed successfully");
    }


}