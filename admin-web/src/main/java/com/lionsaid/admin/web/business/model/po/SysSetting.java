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
@Table(name = "SysSetting", indexes = {
        @Index(name = "idx_syssetting_settingkey", columnList = "settingKey")
})
public class SysSetting  extends Auditable {
    @Id
    @Column(name = "id", nullable = false)
    private String id;
    private String settingKey;
    private String settingValue;
}
