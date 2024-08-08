package com.lionsaid.admin.web.business.service.impl;

import com.lionsaid.admin.web.business.model.po.SysCOS;
import com.lionsaid.admin.web.business.repository.SysCOSRepository;
import com.lionsaid.admin.web.business.service.COSService;
import com.lionsaid.admin.web.common.IServiceImpl;
import com.qcloud.cos.COSClient;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
@AllArgsConstructor
public class COSServiceImpl extends IServiceImpl<SysCOS, String, SysCOSRepository> implements COSService {

    private final COSClient cosClient;

    @Override
    public void delete(String id) {
        SysCOS oos = repository.getReferenceById(id);
        if (ObjectUtils.allNotNull(oos)) {
            repository.deleteById(id);
            cosClient.deleteObject(oos.getBucket(), oos.getObject());
        }
    }

    @Override
    @SneakyThrows
    public SysCOS saveAndFlush(SysCOS sysOOS, MultipartFile file) {
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        File tempFile = File.createTempFile(sysOOS.getObject(), suffix);
        file.transferTo(tempFile);
        sysOOS.setObject(sysOOS.getObject() + suffix);
        cosClient.putObject(sysOOS.getBucket(), sysOOS.getObject(), tempFile);
        tempFile.deleteOnExit();
        sysOOS.setExpiredDateTime(LocalDateTime.now().plusDays(30));
        URL url = cosClient.generatePresignedUrl(sysOOS.getBucket(), sysOOS.getObject(), Date.from(sysOOS.getExpiredDateTime().atZone(ZoneId.systemDefault()).toInstant()));
        sysOOS.setUrl(url.toString());
        sysOOS.setName(file.getOriginalFilename());
        sysOOS.setContentType(file.getContentType());
        if (StringUtils.isEmpty(sysOOS.getObject())) {
            sysOOS.setObject(file.getOriginalFilename());
        }
        return repository.saveAndFlush(sysOOS);
    }

    @Override
    @Cacheable(value = "my-redis-cache1-day", unless = "#result==null")
    public String getUrl(String id) {
        Optional<SysCOS> optional = repository.findById(id);
        if (optional.isPresent()) {
            return optional.get().getUrl();
        } else {
            return id;
        }
    }


}