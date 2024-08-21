package com.lionsaid.admin.web.business.model.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.lionsaid.admin.web.business.model.po.SysMenu}
 */
@Value
public class SysMenuDto implements Serializable {
    String id;
    String name;
    String tag;
    String icon;
    String image;
    String route;
    String routeType;
    String summary;
    Integer type;
    Integer status;
    Integer version;

    public SysMenuDto(String id, String name, String tag, String icon, String image, String route, String routeType, String summary, Integer type, Integer status, Integer version) {
        this.id = id;
        this.name = name;
        this.tag = tag;
        this.icon = icon;
        this.image = image;
        this.route = route;
        this.routeType = routeType;
        this.summary = summary;
        this.type = type;
        this.status = status;
        this.version = version;
    }
}