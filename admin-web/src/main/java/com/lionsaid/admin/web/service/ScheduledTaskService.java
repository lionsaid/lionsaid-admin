package com.lionsaid.admin.web.service;

import com.alibaba.fastjson2.JSONObject;
import com.lionsaid.admin.web.common.IService;
import com.lionsaid.admin.web.model.po.ScheduledTask;

import java.util.List;

public interface ScheduledTaskService extends IService<ScheduledTask, Long> {


    void removeTask(Long taskId);

    List< JSONObject> getRunTask();

    void stopTask(Long taskId);

    void startTask(Long taskId);

    void updateTask(ScheduledTask scheduledTask);
}

