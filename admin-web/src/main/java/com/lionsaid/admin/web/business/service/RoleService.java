package com.lionsaid.admin.web.business.service;

import com.lionsaid.admin.web.business.model.po.SysRole;
import com.lionsaid.admin.web.common.IService;

import java.util.List;

public interface RoleService extends IService<SysRole, String> {
    void postRoleJoin(String roleId, List<String> joinId);

    void deleteRoleJoin(List<String> id);

    List<SysRole> getRoleJoin(List<String> joinId);
}
