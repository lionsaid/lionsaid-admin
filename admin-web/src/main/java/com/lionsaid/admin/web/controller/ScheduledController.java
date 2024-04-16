package com.lionsaid.admin.web.controller;

import com.alibaba.fastjson2.JSONObject;
import com.lionsaid.admin.web.model.po.ScheduledTask;
import com.lionsaid.admin.web.service.ScheduledTaskService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author sunwei
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/scheduled")
public class ScheduledController {
    private final ScheduledTaskService scheduledTaskService;


    @PostMapping("/save")
    public ResponseEntity<String> addTask(@RequestBody ScheduledTask task) {
        scheduledTaskService.saveAndFlush(task);
        return ResponseEntity.ok("Task added successfully");
    }

    @PutMapping("/stopTask/{taskId}")
    public ResponseEntity<String> stopTask(@PathVariable Long taskId) {
        scheduledTaskService.stopTask(taskId);
        return ResponseEntity.ok("Task added successfully");
    }

    @PutMapping("/startTask/{taskId}")
    public ResponseEntity<String> startTask(@PathVariable Long taskId) {
        scheduledTaskService.startTask(taskId);
        return ResponseEntity.ok("Task stop successfully");
    }

    @GetMapping("/getRunTask")
    public ResponseEntity<List<JSONObject>> getRunTask() {
        return ResponseEntity.ok(scheduledTaskService.getRunTask());
    }

    @DeleteMapping("/remove/{taskId}")
    public ResponseEntity<String> removeTask(@PathVariable Long taskId) {
        scheduledTaskService.removeTask(taskId);
        return ResponseEntity.ok("Task removed successfully");
    }


}