package com.lionsaid.admin.web.business.service.impl;

import com.google.common.collect.Lists;
import com.lionsaid.admin.web.business.model.po.SysMenu;
import com.lionsaid.admin.web.business.model.po.SysMenuJoin;
import com.lionsaid.admin.web.business.repository.SysMenuJoinRepository;
import com.lionsaid.admin.web.business.repository.SysMenuRepository;
import com.lionsaid.admin.web.business.repository.SysRoleJoinRepository;
import com.lionsaid.admin.web.business.service.MenuService;
import com.lionsaid.admin.web.common.IServiceImpl;
import com.lionsaid.admin.web.utils.LionSaidIdGenerator;
import lombok.AllArgsConstructor;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class MenuServiceImpl extends IServiceImpl<SysMenu, String, SysMenuRepository> implements MenuService {
    private final SysMenuJoinRepository sysMenuJoinRepository;
    private final SysRoleJoinRepository sysRoleJoinRepository;

    @Override
    public void postMenuJoin(String menuId, List<String> joinId) {
        if (repository.existsById(menuId)) {
            ArrayList<@Nullable SysMenuJoin> list = Lists.newArrayList();
            joinId.forEach(o -> {
                SysMenuJoin sysMenuJoin = SysMenuJoin.builder()
                        .menuId(menuId)
                        .joinId(o)
                        .id(LionSaidIdGenerator.snowflakeId()).build();
                list.add(sysMenuJoin);
            });
            sysMenuJoinRepository.saveAllAndFlush(list);
        }
    }

    @Override
    public void deleteMenuJoin(List<String> id) {
        sysMenuJoinRepository.deleteAllByIdInBatch(id);
    }

    @Override
    public List<SysMenu> getMenuJoin(List<String> joinId) {
        return sysMenuJoinRepository.findByJoinId(joinId);
    }

    @Override
    public Object getUserMenu(String userId) {
        ArrayList<@Nullable String> joinId = Lists.newArrayList();
        joinId.add(userId);
        sysRoleJoinRepository.findByJoinId(joinId).forEach(o -> {
            joinId.add(o.getId());
        });
        return sysMenuJoinRepository.findByJoinId(joinId);
    }

    @Override
    public void delete(String id) {
        if (!sysMenuJoinRepository.existsByMenuId(id)) {
            sysMenuJoinRepository.deleteById(id);
        }
    }
}
