package com.lionsaid.admin.web.business.service.impl;

import com.lionsaid.admin.web.common.IServiceImpl;
import com.lionsaid.admin.web.business.model.po.SysMenu;
import com.lionsaid.admin.web.business.repository.SysMenuRepository;
import com.lionsaid.admin.web.business.service.MenuService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MenuServiceImpl extends IServiceImpl<SysMenu, Long, SysMenuRepository> implements MenuService {

}
