package com.lionsaid.admin.web.business.model.po;

import com.lionsaid.admin.web.common.Auditable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "sys_user_device_info")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysUserDeviceInfo extends Auditable {
    @Id
    private String id;
    private String targetPlatform;
    private String deviceInfo;
    private String deviceId;
    private String userId;
}
