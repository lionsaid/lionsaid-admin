package com.lionsaid.admin.web.business.service.impl;

import com.lionsaid.admin.web.business.model.dto.SearchDTO;
import com.lionsaid.admin.web.business.model.po.BusinessProjectInfo;
import com.lionsaid.admin.web.business.model.po.BusinessProjectInfoJoin;
import com.lionsaid.admin.web.business.model.po.BusinessProjectInfoStar;
import com.lionsaid.admin.web.business.repository.BusinessProjectInfoJoinRepository;
import com.lionsaid.admin.web.business.repository.BusinessProjectInfoRepository;
import com.lionsaid.admin.web.business.repository.BusinessProjectInfoStarRepository;
import com.lionsaid.admin.web.business.service.BusinessProjectInfoService;
import com.lionsaid.admin.web.common.IServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class BusinessProjectInfoServiceImpl extends IServiceImpl<BusinessProjectInfo, String, BusinessProjectInfoRepository> implements BusinessProjectInfoService {

    private final BusinessProjectInfoJoinRepository businessProjectInfoJoinRepository;
    private final BusinessProjectInfoStarRepository businessProjectInfoStarRepository;

    @Override
    public void deletebusinessProjectJoin(List<String> ids) {
        businessProjectInfoJoinRepository.deleteAllByIdInBatch(ids);
    }

    @Override
    public void postbusinessProjectJoin(String id, String joinId) {
        if (!businessProjectInfoJoinRepository.existsByProjectIdAndJoinId(id, joinId)) {
            businessProjectInfoJoinRepository.saveAndFlush(BusinessProjectInfoJoin.builder().projectId(id).joinId(joinId).build());
        }
    }

    @Override
    public void star(String userId, String id) {
        if (businessProjectInfoStarRepository.existsByProjectIdAndJoinId(id, userId)) {
            businessProjectInfoStarRepository.deleteByProjectIdAndJoinId(id, userId);
            repository.updateStarById(-1, id);
        } else {
            businessProjectInfoStarRepository.saveAndFlush(BusinessProjectInfoStar.builder().projectId(id).joinId(userId).build());
            repository.updateStarById(1, id);

        }

    }

    @Override
    public Page<BusinessProjectInfo> findByUserProjectInfo(SearchDTO searchDTO, Pageable pageable) {
        return repository.findByUserProjectInfo(searchDTO, pageable);
    }

    @Override
    public List<String> findByUserStar(SearchDTO searchDTO) {
        return businessProjectInfoStarRepository.findByJoinId(searchDTO.userId);
    }


}
