package com.lionsaid.admin.web.business.service.impl;

import com.lionsaid.admin.web.business.model.po.BusinessCardInfo;
import com.lionsaid.admin.web.business.repository.BusinessCardInfoRepository;
import com.lionsaid.admin.web.business.service.BusinessCardInfoService;
import com.lionsaid.admin.web.common.IServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class BusinessCardInfoServiceImpl extends IServiceImpl<BusinessCardInfo, String, BusinessCardInfoRepository> implements BusinessCardInfoService {


    @Override
    public Page<BusinessCardInfo> findByType(String type, Pageable pageable) {
        return repository.findByType(type, pageable);
    }
}
