package com.lionsaid.admin.web.business.model.po;

import com.lionsaid.admin.web.common.Auditable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sys_menu")
public class SysMenu extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;
    private String name;
    private String tag;
    private String icon;
    private String image;
    private String groupId;
    private String route;
    private String routeType;
    private String location;
    private String description;
    private String summary;
    private Integer type;
    private Integer sort;
    private Integer status;
    private Integer version;
}
