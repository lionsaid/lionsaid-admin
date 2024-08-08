package com.lionsaid.admin.web.business.service;

import com.lionsaid.admin.web.business.model.po.SysCOS;
import com.lionsaid.admin.web.common.IService;
import lombok.SneakyThrows;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.multipart.MultipartFile;

public interface COSService extends IService<SysCOS, String> {

    void delete(String id);

    @SneakyThrows
    SysCOS saveAndFlush(SysCOS sysOOS, MultipartFile file);


    @Cacheable(value = "my-redis-cache1-day", unless = "#result==null")
    String getUrl(String id);
}
