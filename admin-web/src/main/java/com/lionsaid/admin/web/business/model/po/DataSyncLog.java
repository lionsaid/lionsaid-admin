package com.lionsaid.admin.web.business.model.po;

import com.lionsaid.admin.web.common.Auditable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "data_sync_log")
public class DataSyncLog  extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID )
    @Column(name = "id", nullable = false)
    private String id;
    private Long jobId;
    private Long success;
    private Long fail;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Long executionTime;
    private String failInfo;
}
