package com.lionsaid.admin.web.business.repository;

import com.lionsaid.admin.web.business.model.dto.SysMenuDto;
import com.lionsaid.admin.web.business.model.po.SysMenu;
import com.lionsaid.admin.web.business.model.po.SysMenuJoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SysMenuJoinRepository extends JpaRepository<SysMenuJoin, String> {

    @Query("select distinct new com.lionsaid.admin.web.business.model.dto.SysMenuDto( a.id,a.name,a.tag,a.icon,a.image ,a.route,a.routeType,a.summary,a.type,a.status,a.version) from SysMenuJoin s join SysMenu a on s.menuId=a.id where s.joinId in ?1")
    List<SysMenuDto> findByJoinId(List<String> joinId);

    @Query("select (count(s) > 0) from SysMenuJoin s where s.menuId = ?1")
    boolean existsByMenuId(String menuId);
}