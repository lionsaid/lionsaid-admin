package com.lionsaid.admin.web.config;

import com.lionsaid.admin.web.business.model.po.SysSetting;
import com.lionsaid.admin.web.business.repository.SysSettingRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
@AllArgsConstructor
@Slf4j
public class AdminRunConfig {
    private final ApplicationContext applicationContext;
    private final SysSettingRepository sysSettingRepository;

    @PostConstruct
    public void systemInit() {
        String key = "systemInit";
        if (!sysSettingRepository.existsBySettingKey(key)) {
            sysSettingRepository.save(SysSetting.builder().id(UUID.randomUUID()).settingKey(key).settingValue(LocalDateTime.now().toString()).build());
        }
    }
}
