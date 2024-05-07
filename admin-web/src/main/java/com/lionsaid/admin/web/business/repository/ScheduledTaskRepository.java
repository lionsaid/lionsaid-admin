package com.lionsaid.admin.web.business.repository;

import com.lionsaid.admin.web.business.model.po.ScheduledTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ScheduledTaskRepository extends JpaRepository<ScheduledTask, String> {
    @Query("select s from ScheduledTask s where s.taskStatus = ?1")
    List<ScheduledTask> findByTaskStatus(Integer taskStatus);

    @Transactional
    @Modifying
    @Query("update ScheduledTask s set s.taskStatus = ?1 where s.id = ?2")
    void updateTaskStatusById(Integer taskStatus, String id);
}