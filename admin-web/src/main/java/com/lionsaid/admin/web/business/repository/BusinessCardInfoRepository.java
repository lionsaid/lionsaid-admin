package com.lionsaid.admin.web.business.repository;

import com.lionsaid.admin.web.business.model.po.BusinessCardInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BusinessCardInfoRepository extends JpaRepository<BusinessCardInfo, String> {
    @Query("select b from BusinessCardInfo b where b.type = ?1")
    Page<BusinessCardInfo> findByType(String type, Pageable pageable);
}