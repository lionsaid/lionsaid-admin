package com.lionsaid.admin.web.business.repository;

import com.lionsaid.admin.web.business.model.po.SysRole;
import com.lionsaid.admin.web.business.model.po.SysRoleJoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SysRoleJoinRepository extends JpaRepository<SysRoleJoin, String> {
    @Query("select distinct a from SysRole a join SysRoleJoin s on s.roleId=a.id where s.joinId in ?1")
    List<SysRole> findByJoinId(List<String> joinId);
}