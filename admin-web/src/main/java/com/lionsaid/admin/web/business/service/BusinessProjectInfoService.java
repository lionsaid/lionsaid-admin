package com.lionsaid.admin.web.business.service;

import com.lionsaid.admin.web.business.model.dto.SearchDTO;
import com.lionsaid.admin.web.business.model.po.BusinessProjectInfo;
import com.lionsaid.admin.web.common.IService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BusinessProjectInfoService extends IService<BusinessProjectInfo, String> {


    void deletebusinessProjectJoin(List<String> ids);

    void postbusinessProjectJoin(String id, String s);

    void star(String userId, String id);

    Page<BusinessProjectInfo> findByUserProjectInfo(SearchDTO searchDTO, Pageable pageable);

    List<String> findByUserStar(SearchDTO searchDTO);
}
