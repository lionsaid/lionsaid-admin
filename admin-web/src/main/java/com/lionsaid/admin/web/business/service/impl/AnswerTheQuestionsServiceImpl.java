package com.lionsaid.admin.web.business.service.impl;

import com.lionsaid.admin.web.business.model.po.BusinessQuestionInfo;
import com.lionsaid.admin.web.business.repository.BusinessQuestionInfoRepository;
import com.lionsaid.admin.web.business.service.AnswerTheQuestionsService;
import com.lionsaid.admin.web.common.IServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AnswerTheQuestionsServiceImpl extends IServiceImpl<BusinessQuestionInfo, String, BusinessQuestionInfoRepository> implements AnswerTheQuestionsService {

    @Override
    public Object getUserAnswerTheQuestions(String userId) {
        return null;
    }

    @Override
    public void deleteAnswerTheQuestionsJoin(List<String> ids) {

    }

    @Override
    public void postAnswerTheQuestionsJoin(String id, Integer joinId) {

    }

    @Override
    public void star(String id, String userId) {

    }
}
