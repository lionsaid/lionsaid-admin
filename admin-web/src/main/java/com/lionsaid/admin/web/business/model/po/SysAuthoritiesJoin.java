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
@Table(name = "sys_authorities_join", indexes = {
        @Index(name = "idx_sysauthoritiesjoin_joinid", columnList = "joinId, authoritiesId")
})
public class SysAuthoritiesJoin  extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;
    private String authoritiesId;
    private String joinId;
}
