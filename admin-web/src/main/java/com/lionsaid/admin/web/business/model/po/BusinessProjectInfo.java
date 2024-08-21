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
@Table(name = "business_project_info", indexes = {})
public class BusinessProjectInfo extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;
    private String name;
    private String tag;
    private String route;
    private String routeType;
    private String icon;
    private String backgroundImage;
    private String description;
    private String summary;
    private Integer type;
    private Integer sort;
    private Integer star;
    private Integer status;
}
