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
@Table(name = "sys_log")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysLog extends Auditable {
    @Id
    private String id;
    @Lob
    private String result;
    private String requestId;
    private String path;
    private String name;
    private String method;
    private Long executionSeconds;
    private String description;
    @Lob
    private String param;
    @Lob
    private String additionalInfo;
    private LocalDateTime expiredDateTime;
}
