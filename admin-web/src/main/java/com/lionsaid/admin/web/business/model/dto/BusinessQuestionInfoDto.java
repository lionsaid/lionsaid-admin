package com.lionsaid.admin.web.business.model.dto;

import com.lionsaid.admin.web.business.model.po.BusinessQuestionInfo;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link BusinessQuestionInfo}
 */
@Value
public class BusinessQuestionInfoDto implements Serializable {
    String id;
    String content;
    String answer;
    String hint;
    String analysis;
    String type;
    String tag;
    String format;
    String language;
    Integer weights;
    String difficulty;
    Integer status;
    Integer sort;
    Integer star;
    Integer joinId;
}