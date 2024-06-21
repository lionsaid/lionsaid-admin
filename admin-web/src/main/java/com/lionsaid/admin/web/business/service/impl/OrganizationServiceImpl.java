package com.lionsaid.admin.web.business.service.impl;

import com.google.common.collect.Lists;
import com.lionsaid.admin.web.business.model.po.SysOrganization;
import com.lionsaid.admin.web.business.model.po.SysOrganizationJoin;
import com.lionsaid.admin.web.business.repository.SysOrganizationJoinRepository;
import com.lionsaid.admin.web.business.repository.SysOrganizationRepository;
import com.lionsaid.admin.web.business.service.OrganizationService;
import com.lionsaid.admin.web.common.IServiceImpl;
import com.lionsaid.admin.web.utils.LionSaidIdGenerator;
import lombok.AllArgsConstructor;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class OrganizationServiceImpl extends IServiceImpl<SysOrganization, String, SysOrganizationRepository> implements OrganizationService {

    private final SysOrganizationJoinRepository sysOrganizationJoinRepository;

    @Override
    public void postOrganizationJoin(String organizationId, List<String> joinId) {
        if (repository.existsById(organizationId)) {
            ArrayList<@Nullable SysOrganizationJoin> list = Lists.newArrayList();
            joinId.forEach(o -> {
                SysOrganizationJoin sysOrganizationJoin = SysOrganizationJoin.builder()
                        .organizationId(organizationId)
                        .joinId(o)
                        .id(LionSaidIdGenerator.snowflakeId()).build();
                list.add(sysOrganizationJoin);
            });
            sysOrganizationJoinRepository.saveAllAndFlush(list);
        }
    }

    @Override
    public void deleteOrganizationJoin(List<String> id) {
        sysOrganizationJoinRepository.deleteAllByIdInBatch(id);
    }

    @Override
    public List<SysOrganization> getOrganizationJoin(List<String> joinId) {
        return sysOrganizationJoinRepository.findByJoinId(joinId);
    }
}
