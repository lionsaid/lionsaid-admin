package com.lionsaid.admin.web.business.repository;

import com.lionsaid.admin.web.business.model.dto.SearchDTO;
import com.lionsaid.admin.web.business.model.po.BusinessProjectInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface BusinessProjectInfoRepository extends JpaRepository<BusinessProjectInfo, String> {
    @Query("""
            select distinct b
            from BusinessProjectInfo b 
            left join BusinessProjectInfoJoin c on b.id = c.projectId 
            where 
            (c.joinId in :#{#param.authorities} or b.createdBy = :#{#param.userId})
            """)
    Page<BusinessProjectInfo> findByUserProjectInfo(@Param(value = "param") SearchDTO param, Pageable pageable);

    @Transactional
    @Modifying
    @Query("update BusinessProjectInfo b set b.star =b.star+?1 where b.id = ?2")
    int updateStarById(Integer star, String id);

}