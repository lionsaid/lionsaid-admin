package com.lionsaid.admin.web.repository;

import com.lionsaid.admin.web.model.po.DataSyncLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataSyncLogRepository extends JpaRepository<DataSyncLog, Long> {
}