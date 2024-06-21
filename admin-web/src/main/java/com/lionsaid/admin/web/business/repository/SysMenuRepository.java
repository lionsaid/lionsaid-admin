package com.lionsaid.admin.web.business.repository;

import com.lionsaid.admin.web.business.model.po.SysMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SysMenuRepository extends JpaRepository<SysMenu, String> {
    @Query(value = """
            select m from SysMenu m join SysMenuJoin  b on m.id=b.menuId where b.joinId in ?1
            """)
    Optional<SysMenu> findByUserMenu(List<String> joinId);
}