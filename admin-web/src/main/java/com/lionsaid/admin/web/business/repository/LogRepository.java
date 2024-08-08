package com.lionsaid.admin.web.business.repository;

import com.lionsaid.admin.web.business.model.po.SysLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface LogRepository extends JpaRepository<SysLog, String> {
    @Transactional
    @Modifying
    @Query("update SysLog s set s.executionSeconds = ?1 where s.id = ?2")
    void updateExecutionSecondsById(Long executionSeconds, String id);
}