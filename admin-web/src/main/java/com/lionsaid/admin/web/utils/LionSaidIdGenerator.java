package com.lionsaid.admin.web.utils;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Slf4j
@Data
public final class LionSaidIdGenerator {

    private static final String DATACENTER_ID = RandomStringUtils.randomNumeric(6);
    private static final String MACHINE_ID = RandomStringUtils.randomNumeric(6);

    public static String randomBase64UUID() {
        UUID uuid = UUID.randomUUID();
        String uuidStr = uuid.toString();
        return StringUtils.replace(uuidStr, "-", "");
    }

    public static String snowflakeId() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + DATACENTER_ID + MACHINE_ID + RandomStringUtils.randomNumeric(10);
    }


}
