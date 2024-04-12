package com.lionsaid.admin.web.repository;

import com.lionsaid.admin.web.model.po.DataSyncJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DataSyncJobRepository extends JpaRepository<DataSyncJob, Long> {

}