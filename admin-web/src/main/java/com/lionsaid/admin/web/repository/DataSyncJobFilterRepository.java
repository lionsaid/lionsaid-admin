package com.lionsaid.admin.web.repository;

import com.lionsaid.admin.web.model.po.DataSyncJobFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DataSyncJobFilterRepository extends JpaRepository<DataSyncJobFilter, Long> {
    @Query("select d from DataSyncJobFilter d where d.jobId = ?1 order by  d.sort")
    List<DataSyncJobFilter> findByJobId(Long jobId);
}