package com.lionsaid.admin.web.repository;

import com.lionsaid.admin.web.model.po.DataSyncJob;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataSyncJobRepository extends JpaRepository<DataSyncJob, Long> {
}