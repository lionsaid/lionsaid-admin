package com.lionsaid.admin.web.business.service.impl;

import com.google.common.collect.Lists;
import com.lionsaid.admin.web.business.model.po.SysAuthorities;
import com.lionsaid.admin.web.business.model.po.SysAuthoritiesJoin;
import com.lionsaid.admin.web.business.repository.SysAuthoritiesJoinRepository;
import com.lionsaid.admin.web.business.repository.SysAuthoritiesRepository;
import com.lionsaid.admin.web.business.service.AuthoritiesService;
import com.lionsaid.admin.web.common.IServiceImpl;
import com.lionsaid.admin.web.utils.LionSaidIdGenerator;
import lombok.AllArgsConstructor;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AuthoritiesServiceImpl extends IServiceImpl<SysAuthorities, String, SysAuthoritiesRepository> implements AuthoritiesService {

    private final SysAuthoritiesJoinRepository sysAuthoritiesJoinRepository;

    @Override
    public void postAuthoritiesJoin(String AuthoritiesId, List<String> joinId) {
        if (repository.existsById(AuthoritiesId)) {
            ArrayList<@Nullable SysAuthoritiesJoin> list = Lists.newArrayList();
            joinId.forEach(o -> {
                SysAuthoritiesJoin sysAuthoritiesJoin = SysAuthoritiesJoin.builder()
                        .authoritiesId(AuthoritiesId)
                        .joinId(o)
                        .id(LionSaidIdGenerator.snowflakeId()).build();
                list.add(sysAuthoritiesJoin);
            });
            sysAuthoritiesJoinRepository.saveAllAndFlush(list);
        }
    }

    @Override
    public void deleteAuthoritiesJoin(List<String> id) {
        sysAuthoritiesJoinRepository.deleteAllByIdInBatch(id);
    }

    @Override
    public List<SysAuthorities> getAuthoritiesJoin(List<String> joinId) {
        return sysAuthoritiesJoinRepository.findByJoinId(joinId);
    }
}
