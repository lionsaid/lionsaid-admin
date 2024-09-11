package com.lionsaid.admin.web.business.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Maps;
import com.lionsaid.admin.web.business.model.po.BusinessExtendedInformation;
import com.lionsaid.admin.web.business.repository.BusinessExtendedInformationRepository;
import com.lionsaid.admin.web.business.service.BusinessExtendedInformationService;
import com.lionsaid.admin.web.business.service.COSService;
import com.lionsaid.admin.web.common.IServiceImpl;
import lombok.AllArgsConstructor;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@AllArgsConstructor
public class BusinessExtendedInformationServiceImpl extends IServiceImpl<BusinessExtendedInformation, String, BusinessExtendedInformationRepository> implements BusinessExtendedInformationService {
    private final COSService cosService;

    @Override
    public Map<String, List<JSONObject>> findByJoinId(List<String> joinId) {
        HashMap<@Nullable String, @Nullable List<JSONObject>> map = Maps.newHashMap();
        repository.findByJoinId(joinId).forEach(o -> {
            String name = o.getString("name");
            // 处理包含 "cos" 的 name
            if (name.contains("cos")) {
                o.put(name, cosService.getUrl(o.getString(name)));
            }
            // 更新 HashMap：如果 joinId 已存在，则追加到其 value 列表中
            map.computeIfAbsent(o.getString("joinId"), key -> new ArrayList<>()).add(o);
        });
        return map;
    }
}
