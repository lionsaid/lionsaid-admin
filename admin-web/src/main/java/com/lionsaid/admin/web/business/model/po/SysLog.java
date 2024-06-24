package com.lionsaid.admin.web.business.model.po;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "sys_log")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysLog {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;
    @Lob
    private String result;
    private String requestId;
    private String path;
    private String name;
    private String method;
    private String description;
    @Lob
    private String param;
    @CreatedBy
    private String createdBy;
    @Lob
    private String additionalInfo;
    private LocalDateTime expiredDateTime;
    @CreatedDate
    private LocalDateTime createdDate;


}
