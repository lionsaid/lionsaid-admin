package com.lionsaid.admin.web.business.model.po;

import com.lionsaid.admin.web.common.Auditable;
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
@Table(name = "data_sync_data_source")
public class DataSyncDataSource  extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    @SequenceGenerator(name = "id_generator", sequenceName = "data_sync_sequence_name", allocationSize = 1, initialValue = 100000)
    @Column(name = "id", nullable = false)
    private Long id;
    private String url;
    private String password;
    private String username;
    private String driverClassName;
    private String sourceType;
}
