package com.lionsaid.admin.web.business.model.po;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link BusinessExtendedInformation}
 */
@Value
public class BusinessExtendedInformationDto implements Serializable {
    String id;
    String joinId;
    String name;
    String content;
    String contentType;
    int status;
}