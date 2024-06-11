package com.lionsaid.admin.web.business.model.po;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(indexes = {@Index(name = "idx_user_username_unq", columnList = "username", unique = true)})
public class SysSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID, generator = "id_generator")
    @Column(name = "id", nullable = false)
    private UUID id;
    private String settingKey;
    private String settingValue;
}
