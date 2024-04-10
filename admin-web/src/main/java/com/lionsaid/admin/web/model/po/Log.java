package com.lionsaid.admin.web.model.po;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "sys_log")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID id;
    @Lob
    private String result;
    private String requestId;
    private String path;
    private String name;
    private String method;
    private String description;
    @Lob
    private String param;
    private String createdBy;
    @Lob
    private String additionalInfo;
    private LocalDateTime expiredDateTime;
    private LocalDateTime createdDate;


}
