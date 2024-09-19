package com.lionsaid.admin.web.business.model.po;

import com.lionsaid.admin.web.common.Auditable;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "business_extended_information", indexes = {
        @Index(name = "idx_businessextendedinformation", columnList = "joinId")
})
public class BusinessExtendedInformation extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;
    private String joinId;
    private String name;
    private String content;
    private String contentType;
    private int status;
}
