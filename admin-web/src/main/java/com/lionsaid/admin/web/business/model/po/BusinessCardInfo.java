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
@Table(name = "business_card_info", indexes = {
        @Index(name = "idx_businesscardinfo_type", columnList = "type")
})
public class BusinessCardInfo extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;
    private String name;
    private String tag;
    private String content;
    private String type;
    private Integer sort;
    private Integer star;
    private Integer status;
}
