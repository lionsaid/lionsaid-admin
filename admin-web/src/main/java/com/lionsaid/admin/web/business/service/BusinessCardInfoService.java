package com.lionsaid.admin.web.business.service;

import com.lionsaid.admin.web.business.model.po.BusinessCardInfo;
import com.lionsaid.admin.web.common.IService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BusinessCardInfoService extends IService<BusinessCardInfo, String> {
    Page<BusinessCardInfo> findByType(String type, Pageable pageable);

}
