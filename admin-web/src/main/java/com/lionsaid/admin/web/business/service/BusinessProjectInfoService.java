package com.lionsaid.admin.web.business.service;

import com.lionsaid.admin.web.business.model.po.BusinessProjectInfo;
import com.lionsaid.admin.web.common.IService;

import java.util.List;

public interface BusinessProjectInfoService extends IService<BusinessProjectInfo, String> {


    Object getUserbusinessProject(String userId);

    void deletebusinessProjectJoin(List<String> ids);

    void postbusinessProjectJoin(String id, String s);

    void star(String userId, String id);
}
