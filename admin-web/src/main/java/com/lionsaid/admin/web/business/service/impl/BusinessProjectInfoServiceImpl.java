package com.lionsaid.admin.web.business.service.impl;

import com.lionsaid.admin.web.business.model.po.BusinessProjectInfo;
import com.lionsaid.admin.web.business.model.po.BusinessProjectInfoJoin;
import com.lionsaid.admin.web.business.repository.BusinessProjectInfoJoinRepository;
import com.lionsaid.admin.web.business.repository.BusinessProjectInfoRepository;
import com.lionsaid.admin.web.business.service.BusinessProjectInfoService;
import com.lionsaid.admin.web.common.IServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class BusinessProjectInfoServiceImpl extends IServiceImpl<BusinessProjectInfo, String, BusinessProjectInfoRepository> implements BusinessProjectInfoService {

    private final BusinessProjectInfoJoinRepository businessProjectInfoJoinRepository;

    @Override
    public Object getUserbusinessProject(String userId) {
        return null;
    }

    @Override
    public void deletebusinessProjectJoin(List<String> ids) {

    }

    @Override
    public void postbusinessProjectJoin(String id, String s) {

    }

    @Override
    public void star(String userId, String id) {
        if (businessProjectInfoJoinRepository.existsByProjectIdAndJoinId(id, userId)) {
            businessProjectInfoJoinRepository.deleteByProjectIdAndJoinId(id, userId);
        } else {
            businessProjectInfoJoinRepository.saveAndFlush(BusinessProjectInfoJoin.builder().projectId(id).joinId(userId).build());
        }

    }
}
