package com.lionsaid.admin.web.business.model.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lionsaid.admin.web.common.Auditable;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "sys_menu_join", indexes = {
        @Index(name = "idx_sysmenujoin_menuid_joinid", columnList = "menuId, joinId")
})
public class SysMenuJoin  extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;
    private String menuId;
    private String joinId;
}
