package com.lionsaid.admin.web.business.model.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "sys_authorities_join", indexes = {
        @Index(name = "idx_sysauthoritiesjoin_joinid", columnList = "joinId, authoritiesId")
})
public class SysAuthoritiesJoin {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;
    private String authoritiesId;
    private String joinId;
    @CreatedBy
    private String createdBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedBy
    private String lastModifiedBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

}
