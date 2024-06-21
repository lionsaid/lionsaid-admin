package com.lionsaid.admin.web.business.repository;

import com.lionsaid.admin.web.business.model.po.SysAuthorities;
import com.lionsaid.admin.web.business.model.po.SysAuthoritiesJoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SysAuthoritiesJoinRepository extends JpaRepository<SysAuthoritiesJoin, String> {
    @Query("select a from SysAuthoritiesJoin s join SysAuthorities a on s.authoritiesId=a.id where s.joinId in ?1")
    List<SysAuthorities> findByJoinId(List<String> joinId);

    @Query("select (count(s) > 0) from SysAuthoritiesJoin s where s.joinId = ?1")
    boolean existsByMenuId(String joinId);
}