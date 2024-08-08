package com.lionsaid.admin.web.business.repository;

import com.lionsaid.admin.web.business.model.po.SysCOS;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SysCOSRepository extends JpaRepository<SysCOS, String> {
    @Override
    Optional<SysCOS> findById(String s);
}