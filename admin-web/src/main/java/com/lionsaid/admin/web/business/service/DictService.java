package com.lionsaid.admin.web.business.service;

import com.lionsaid.admin.web.business.model.po.SysDict;
import com.lionsaid.admin.web.common.IService;

public interface DictService extends IService<SysDict, String> {
    String findByDictIndexAndLanguage(String dictIndex, String language);
}
