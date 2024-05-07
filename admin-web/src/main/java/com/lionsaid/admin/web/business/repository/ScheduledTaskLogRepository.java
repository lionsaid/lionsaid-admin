package com.lionsaid.admin.web.business.repository;

import com.lionsaid.admin.web.business.model.po.ScheduledTaskLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduledTaskLogRepository extends JpaRepository<ScheduledTaskLog, String> {
}