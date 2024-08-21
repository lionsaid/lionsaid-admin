package com.lionsaid.admin.web.business.model.po;

import com.lionsaid.admin.web.common.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "sys_cos")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysCOS extends Auditable {
    @Id
    private String id;
    private String bucket;
    private String object;
    private String name;
    private String contentType;
    @Column(length = 500)
    private String url;
    private LocalDateTime expiredDateTime;

}
