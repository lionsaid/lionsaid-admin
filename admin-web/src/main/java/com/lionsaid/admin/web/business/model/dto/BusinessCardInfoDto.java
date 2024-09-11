package com.lionsaid.admin.web.business.model.dto;

import com.lionsaid.admin.web.business.model.po.BusinessCardInfo;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link BusinessCardInfo}
 */
@Value
public class BusinessCardInfoDto implements Serializable {
    String id;
    String name;
    String tag;
    String content;
    String type;
}