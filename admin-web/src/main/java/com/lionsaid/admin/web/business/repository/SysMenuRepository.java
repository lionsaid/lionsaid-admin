package com.lionsaid.admin.web.business.repository;

import com.lionsaid.admin.web.business.model.po.SysMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysMenuRepository extends JpaRepository<SysMenu, Long> {
}