package com.lionsaid.admin.web.business.repository;

import com.lionsaid.admin.web.business.model.po.SysAuthorities;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysAuthoritiesRepository extends JpaRepository<SysAuthorities, String> {
}