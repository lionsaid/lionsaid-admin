package com.lionsaid.admin.web.business.repository;

import com.lionsaid.admin.web.business.model.po.BusinessProjectInfoStar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BusinessProjectInfoStarRepository extends JpaRepository<BusinessProjectInfoStar, String> {
    @Query("select distinct b.projectId from BusinessProjectInfoStar b where b.joinId = ?1")
    List<String> findByJoinId(String joinId);

    @Query("select (count(b) > 0) from BusinessProjectInfoStar b where b.projectId = ?1 and b.joinId = ?2")
    boolean existsByProjectIdAndJoinId(String projectId, String joinId);

    @Transactional
    @Modifying
    @Query("delete from BusinessProjectInfoStar b where b.projectId = ?1 and b.joinId = ?2")
    void deleteByProjectIdAndJoinId(String projectId, String joinId);
}