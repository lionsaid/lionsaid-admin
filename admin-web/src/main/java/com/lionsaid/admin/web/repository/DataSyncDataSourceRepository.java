package com.lionsaid.admin.web.repository;

import com.lionsaid.admin.web.model.po.DataSyncDataSource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataSyncDataSourceRepository extends JpaRepository<DataSyncDataSource, Long> {
}