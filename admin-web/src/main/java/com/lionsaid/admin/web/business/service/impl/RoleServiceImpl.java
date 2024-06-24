package com.lionsaid.admin.web.business.service.impl;

import com.google.common.collect.Lists;
import com.lionsaid.admin.web.business.model.po.SysRole;
import com.lionsaid.admin.web.business.model.po.SysRoleJoin;
import com.lionsaid.admin.web.business.repository.SysRoleJoinRepository;
import com.lionsaid.admin.web.business.repository.SysRoleRepository;
import com.lionsaid.admin.web.business.service.RoleService;
import com.lionsaid.admin.web.common.IServiceImpl;
import com.lionsaid.admin.web.utils.LionSaidIdGenerator;
import lombok.AllArgsConstructor;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class RoleServiceImpl extends IServiceImpl<SysRole, String, SysRoleRepository> implements RoleService {

    private final SysRoleJoinRepository sysRoleJoinRepository;

    @Override
    public void postRoleJoin(String RoleId, List<String> joinId) {
        if (repository.existsById(RoleId)) {
            ArrayList<@Nullable SysRoleJoin> list = Lists.newArrayList();
            joinId.forEach(o -> {
                SysRoleJoin sysRoleJoin = SysRoleJoin.builder()
                        .roleId(RoleId)
                        .joinId(o)
                        .id(LionSaidIdGenerator.snowflakeId()).build();
                list.add(sysRoleJoin);
            });
            sysRoleJoinRepository.saveAllAndFlush(list);
        }
    }

    @Override
    public void deleteRoleJoin(List<String> id) {
        sysRoleJoinRepository.deleteAllByIdInBatch(id);
    }

    @Override
    public List<SysRole> getRoleJoin(List<String> joinId) {
        return sysRoleJoinRepository.findByJoinId(joinId);
    }


}
