package com.lionsaid.admin.web.business.repository;

import com.lionsaid.admin.web.business.model.po.SysMenu;
import com.lionsaid.admin.web.business.model.po.SysMenuJoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SysMenuJoinRepository extends JpaRepository<SysMenuJoin, String> {
    @Query("select a from SysMenuJoin s join SysMenu a on s.menuId=a.id where s.joinId in ?1")
    List<SysMenu> findByJoinId(List<String> joinId);

    @Query("select (count(s) > 0) from SysMenuJoin s where s.menuId = ?1")
    boolean existsByMenuId(String menuId);
}