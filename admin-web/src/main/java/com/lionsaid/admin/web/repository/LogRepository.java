package com.lionsaid.admin.web.repository;

import com.lionsaid.admin.web.model.po.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LogRepository extends JpaRepository<Log, UUID> {
}