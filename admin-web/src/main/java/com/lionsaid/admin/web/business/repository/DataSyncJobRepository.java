package com.lionsaid.admin.web.business.repository;

import com.lionsaid.admin.web.business.model.po.DataSyncJob;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataSyncJobRepository extends JpaRepository<DataSyncJob, Long> {

}