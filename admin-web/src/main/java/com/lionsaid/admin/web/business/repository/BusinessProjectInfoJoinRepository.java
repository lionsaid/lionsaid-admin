package com.lionsaid.admin.web.business.repository;

import com.lionsaid.admin.web.business.model.po.BusinessProjectInfoJoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface BusinessProjectInfoJoinRepository extends JpaRepository<BusinessProjectInfoJoin, String> {
    @Query("select (count(b) > 0) from BusinessProjectInfoJoin b where b.projectId = ?1 and b.joinId = ?2")
    boolean existsByProjectIdAndJoinId(String projectId, String joinId);

    @Transactional
    @Modifying
    @Query("delete from BusinessProjectInfoJoin b where b.projectId = ?1 and b.joinId = ?2")
    void deleteByProjectIdAndJoinId(String projectId, String joinId);
}