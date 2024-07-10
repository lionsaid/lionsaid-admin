package com.lionsaid.admin.web.business.service.impl;

import com.lionsaid.admin.web.business.model.po.SysDict;
import com.lionsaid.admin.web.business.repository.SysDictRepository;
import com.lionsaid.admin.web.business.service.DictService;
import com.lionsaid.admin.web.common.IServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DictServiceImpl extends IServiceImpl<SysDict, String, SysDictRepository> implements DictService {
    @Cacheable(value = "my-redis-cache2-hours", unless = "#result==null")
    @Override
    public String findByDictIndexAndLanguage(String dictIndex, String language) {
        List<SysDict> list = repository.findByDictIndexAndLanguage(dictIndex, language);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0).getDictWord();
        }
    }
}
