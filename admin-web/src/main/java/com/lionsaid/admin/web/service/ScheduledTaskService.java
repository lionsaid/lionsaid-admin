package com.lionsaid.admin.web.service;

import com.alibaba.fastjson2.JSONObject;
import com.lionsaid.admin.web.common.IService;
import com.lionsaid.admin.web.model.po.ScheduledTask;

import java.util.List;

public interface ScheduledTaskService extends IService<ScheduledTask, String> {


    void removeTask(String taskId);

    List< JSONObject> getRunTask();

    void stopTask(String taskId);

    void startTask(String taskId);

    void updateTask(ScheduledTask scheduledTask);
}

