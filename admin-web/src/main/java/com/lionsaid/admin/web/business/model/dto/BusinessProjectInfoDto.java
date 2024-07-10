package com.lionsaid.admin.web.business.model.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.lionsaid.admin.web.business.model.po.BusinessProjectInfo}
 */
@Value
public class BusinessProjectInfoDto implements Serializable {
    String id;
    String name;
    String tag;
    String route;
    String routeType;
    String location;
    String icon;
    String backgroundImage;
    String description;
    String summary;
    String authorities;
    Integer type;
    Integer sort;
    Integer star;
    Integer status;
}