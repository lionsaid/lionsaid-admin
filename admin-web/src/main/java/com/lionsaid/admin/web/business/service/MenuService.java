package com.lionsaid.admin.web.business.service;

import com.lionsaid.admin.web.business.model.dto.SysMenuDto;
import com.lionsaid.admin.web.business.model.po.SysMenu;
import com.lionsaid.admin.web.common.IService;

import java.util.List;

public interface MenuService extends IService<SysMenu, String> {

    void postMenuJoin(String menuId, List<String> joinId);

    void deleteMenuJoin(List<String> id);


    List<SysMenuDto> getUserMenu(List<String> userId);

    void delete(String id);
}
