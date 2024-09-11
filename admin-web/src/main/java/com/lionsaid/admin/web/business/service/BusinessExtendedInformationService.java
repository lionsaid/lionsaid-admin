package com.lionsaid.admin.web.business.service;

import com.alibaba.fastjson2.JSONObject;
import com.lionsaid.admin.web.business.model.po.BusinessCardInfo;
import com.lionsaid.admin.web.business.model.po.BusinessExtendedInformation;
import com.lionsaid.admin.web.common.IService;

import java.util.List;
import java.util.Map;

public interface BusinessExtendedInformationService extends IService<BusinessExtendedInformation, String> {

    Map<String, List<JSONObject>> findByJoinId(List<String> joinId);
}
