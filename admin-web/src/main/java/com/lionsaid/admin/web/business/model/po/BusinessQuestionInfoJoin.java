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
@Table(name = "business_question_info_join", indexes = {
        @Index(name = "idx_businessquestioninfojoin", columnList = "joinId")
})
public class BusinessQuestionInfoJoin extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;
    private String QuestionId;
    private String joinId;
}
