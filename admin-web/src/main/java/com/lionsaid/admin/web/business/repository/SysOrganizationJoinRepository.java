package com.lionsaid.admin.web.business.repository;

import com.lionsaid.admin.web.business.model.po.SysOrganization;
import com.lionsaid.admin.web.business.model.po.SysOrganizationJoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SysOrganizationJoinRepository extends JpaRepository<SysOrganizationJoin, String> {
    @Query("select a from SysOrganizationJoin s join SysOrganization a on s.organizationId=a.id where s.joinId in ?1")
    List<SysOrganization> findByJoinId(List<String> joinId);
}