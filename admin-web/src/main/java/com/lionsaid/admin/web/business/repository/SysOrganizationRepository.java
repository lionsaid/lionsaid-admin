package com.lionsaid.admin.web.business.repository;

import com.lionsaid.admin.web.business.model.po.SysOrganization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysOrganizationRepository extends JpaRepository<SysOrganization, String> {
}