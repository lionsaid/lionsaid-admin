package com.lionsaid.admin.web.model.po;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "data_sync_job")
public class DataSyncJob {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    @SequenceGenerator(name = "id_generator", sequenceName = "your_sequence_name", allocationSize = 1, initialValue = 100000)
    @Column(name = "id", nullable = false)
    private Long id;
    private Long source;
    private String sourceSql;
    private String targetTable;
    private Long target;
}
