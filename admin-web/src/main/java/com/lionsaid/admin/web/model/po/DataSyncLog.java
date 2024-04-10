package com.lionsaid.admin.web.model.po;

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
public class DataSyncLog {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    @SequenceGenerator(name = "id_generator", sequenceName = "data_sync_log_id", allocationSize = 1, initialValue = 100000)
    @Column(name = "id", nullable = false)
    private Long id;
    private Long jobId;
    private Long success;
    private Long fail;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Long executionTime;
    private String failInfo;
}
