package com.lionsaid.admin.web.business.model.dto;

import com.lionsaid.admin.web.business.model.po.SysCOS;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link SysCOS}
 */
@Value
public class SysCOSDto implements Serializable {
    String bucket;
    String object;
    String name;
    String url;
    LocalDateTime expiredDateTime;

}