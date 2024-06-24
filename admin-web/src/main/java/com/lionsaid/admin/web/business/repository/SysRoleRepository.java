package com.lionsaid.admin.web.business.repository;

import com.lionsaid.admin.web.business.model.po.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SysRoleRepository extends JpaRepository<SysRole, String> {

}