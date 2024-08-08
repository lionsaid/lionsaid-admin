package com.lionsaid.admin.web.config;

import com.alibaba.fastjson2.JSONObject;
import com.lionsaid.admin.web.business.model.po.SysSetting;
import com.lionsaid.admin.web.business.repository.SysSettingRepository;
import com.lionsaid.admin.web.utils.LionSaidIdGenerator;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.region.Region;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Optional;

@Component
@AllArgsConstructor
@Slf4j
public class MyConfig {
    private final ApplicationContext applicationContext;
    private final SysSettingRepository settingRepository;

    @PostConstruct
    public void checkFile() {
        File tempFile = null;
        try {
            tempFile = File.createTempFile(LionSaidIdGenerator.snowflakeId(), ".ttf");
            URL fontUrl = new URL("https://jbrainprod.xian-janssen.com.cn/files/font/microsoft_ya_hei.ttf");
            FileUtils.copyURLToFile(fontUrl, tempFile);
            try (InputStream is = new FileInputStream(tempFile)) {
                Font font = Font.createFont(Font.TRUETYPE_FONT, is);
                log.info("Font loaded successfully: {}", font.getFontName());
            }
        } catch (FontFormatException e) {
            log.error("Font format exception when checking file", e);
            SpringApplication.exit(applicationContext, () -> 1);
        } catch (IOException e) {
            log.error("IO exception when checking file", e);
            SpringApplication.exit(applicationContext, () -> 1);
        } finally {
            if (tempFile != null && tempFile.exists()) {
                if (!tempFile.delete()) {
                    log.warn("Failed to delete temporary file: {}", tempFile.getAbsolutePath());
                }
            }
        }
    }

    @Bean
    public COSClient initCOSClient(
    ) {
        Optional<SysSetting> optional = settingRepository.findBySettingKey("qcloudCOSCredentials");
        if (optional.isPresent()) {
            SysSetting sysSetting = optional.get();
            JSONObject value = JSONObject.parseObject(sysSetting.getSettingValue());
            // 1 初始化用户身份信息（secretId, secretKey）。
            // SECRETID 和 SECRETKEY 请登录访问管理控制台 https://console.cloud.tencent.com/cam/capi 进行查看和管理
            //用户的 SecretId，建议使用子账号密钥，授权遵循最小权限指引，降低使用风险。子账号密钥获取可参见 https://cloud.tencent.com/document/product/598/37140
            //用户的 SecretKey，建议使用子账号密钥，授权遵循最小权限指引，降低使用风险。子账号密钥获取可参见 https://cloud.tencent.com/document/product/598/37140
            COSCredentials cred = new BasicCOSCredentials(value.getString("secretId"), value.getString("secretKey"));
            // 2 设置 bucket 的地域, COS 地域的简称请参见 https://cloud.tencent.com/document/product/436/6224
            // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
            Region region = new Region(value.getString("region"));
            ClientConfig clientConfig = new ClientConfig(region);
            // 这里建议设置使用 https 协议
            // 从 5.6.54 版本开始，默认使用了 https
            clientConfig.setHttpProtocol(HttpProtocol.https);
            // 3 生成 cos 客户端。
            return new COSClient(cred, clientConfig);
        } else {
            return null;
        }
    }


}
