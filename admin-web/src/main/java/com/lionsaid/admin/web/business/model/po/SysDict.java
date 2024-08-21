package com.lionsaid.admin.web.business.model.po;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "sys_dict", indexes = {
        @Index(name = "idx_sysdict_dictindex", columnList = "dictIndex, language")
})
public class SysDict {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;
    private String dictIndex;
    private String dictWord;
    private String language;
    private String groupType;
}
