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
@Table(name = "business_question_info", indexes = {})
public class BusinessQuestionInfo  extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;
    private String content;
    private String answer;
    private String hint;
    private String analysis;
    private String type;
    private String tag;
    private String format;
    private String language;
    private Integer weights;
    private String difficulty;
    private Integer status;
    private Integer sort;
    private Integer star;


}
