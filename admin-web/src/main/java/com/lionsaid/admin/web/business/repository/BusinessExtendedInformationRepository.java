package com.lionsaid.admin.web.business.repository;

import com.alibaba.fastjson2.JSONObject;
import com.lionsaid.admin.web.business.model.po.BusinessExtendedInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BusinessExtendedInformationRepository extends JpaRepository<BusinessExtendedInformation, String> {
    @Query("select b.name AS name,b.id AS id ,b.joinId AS joinId,b.content AS content,b.contentType AS contentType from BusinessExtendedInformation b where b.status=1 and b.joinId in ?1")
    List<JSONObject> findByJoinId(List<String> joinId);
}