package com.lionsaid.admin.web.business.model.po;

import com.lionsaid.admin.web.common.Auditable;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "business_project_info_join", indexes = {
        @Index(name = "idx_businessprojectinfojoin", columnList = "projectId, joinId")
})
public class BusinessProjectInfoJoin extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;
    private String projectId;
    private String joinId;
}
