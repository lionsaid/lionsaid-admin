package com.lionsaid.admin.web.business.service;

import com.lionsaid.admin.web.business.model.po.BusinessProjectInfo;
import com.lionsaid.admin.web.business.model.po.BusinessQuestionInfo;
import com.lionsaid.admin.web.common.IService;

import java.util.List;

public interface AnswerTheQuestionsService extends IService<BusinessQuestionInfo, String> {

    Object getUserAnswerTheQuestions(String userId);

    void deleteAnswerTheQuestionsJoin(List<String> ids);

    void postAnswerTheQuestionsJoin(String id, Integer joinId);

    void star(String id, String userId);
}
