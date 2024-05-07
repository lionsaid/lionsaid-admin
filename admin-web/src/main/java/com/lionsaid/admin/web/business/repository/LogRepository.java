package com.lionsaid.admin.web.business.repository;

import com.lionsaid.admin.web.business.model.po.SysLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LogRepository extends JpaRepository<SysLog, UUID> {
}