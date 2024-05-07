package com.lionsaid.admin.web.business.repository;

import com.lionsaid.admin.web.business.model.po.DataSyncDataSource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataSyncDataSourceRepository extends JpaRepository<DataSyncDataSource, Long> {
}