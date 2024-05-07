package com.lionsaid.admin.web.business.repository;

import com.lionsaid.admin.web.business.model.po.DataSyncLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataSyncLogRepository extends JpaRepository<DataSyncLog, Long> {
}