package com.lionsaid.admin.web.business.repository;

import com.lionsaid.admin.web.business.model.po.SysMailLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface SysMailLogRepository extends JpaRepository<SysMailLog, String> {
    @Query("select count(s) from SysMailLog s where s.to = ?1 and s.type = ?2 and s.localDate =  ?3")
    long countByToAndTypeAndCreatedDate(String to, String type, LocalDate createdDate);
}