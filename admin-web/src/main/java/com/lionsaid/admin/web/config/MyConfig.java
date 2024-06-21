package com.lionsaid.admin.web.config;

import com.lionsaid.admin.web.utils.LionSaidIdGenerator;
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

@Component
@AllArgsConstructor
@Slf4j
public class MyConfig {
    private final ApplicationContext applicationContext;

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
}
