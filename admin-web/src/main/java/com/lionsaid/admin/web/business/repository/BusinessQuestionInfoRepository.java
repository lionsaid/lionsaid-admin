package com.lionsaid.admin.web.business.repository;

import com.lionsaid.admin.web.business.model.po.BusinessQuestionInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessQuestionInfoRepository extends JpaRepository<BusinessQuestionInfo, String> {
}