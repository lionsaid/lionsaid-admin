package com.lionsaid.admin.web.business.model.po;

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
@Table(name = "data_sync_job_filter")
public class DataSyncJobFilter {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    @SequenceGenerator(name = "id_generator", sequenceName = "data_sync_sequence_name", allocationSize = 1, initialValue = 100000)
    @Column(name = "id", nullable = false)
    private Long id;
    private Long jobId;
    private String fieldName;
    private String fieldAlias;
    private String filterType;
    private String setting;
    private Integer sort;

}
