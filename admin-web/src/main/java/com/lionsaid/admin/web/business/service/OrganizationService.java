package com.lionsaid.admin.web.business.service;

import com.lionsaid.admin.web.business.model.po.SysOrganization;
import com.lionsaid.admin.web.common.IService;

import java.util.List;

public interface OrganizationService extends IService<SysOrganization, String> {
    void postOrganizationJoin(String organizationId, List<String> joinId);

    void deleteOrganizationJoin(List<String> id);

    List<SysOrganization> getOrganizationJoin(List<String> joinId);
}
