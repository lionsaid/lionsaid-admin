package com.lionsaid.admin.web.repository;

import com.lionsaid.admin.web.model.po.ScheduledTaskLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduledTaskLogRepository extends JpaRepository<ScheduledTaskLog, String> {
}