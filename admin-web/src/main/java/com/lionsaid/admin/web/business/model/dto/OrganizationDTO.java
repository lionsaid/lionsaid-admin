package com.lionsaid.admin.web.business.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationDTO {
    private String id;
    private String name;
    private String authorities;
    private Integer status;
    private List<String> joinId;

}
