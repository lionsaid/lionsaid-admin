package com.lionsaid.admin.web.business.service;

import com.lionsaid.admin.web.business.model.po.SysAuthorities;
import com.lionsaid.admin.web.common.IService;

import java.util.List;

public interface AuthoritiesService extends IService<SysAuthorities, String> {
    void postAuthoritiesJoin(String authoritiesId, List<String> joinId);

    void deleteAuthoritiesJoin(List<String> id);

    List<SysAuthorities> getAuthoritiesJoin(List<String> joinId);
}
