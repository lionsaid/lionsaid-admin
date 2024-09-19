package com.lionsaid.admin.web.business.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import com.lionsaid.admin.web.annotation.SysLog;
import com.lionsaid.admin.web.business.model.po.BusinessExtendedInformation;
import com.lionsaid.admin.web.business.model.po.BusinessExtendedInformationDto;
import com.lionsaid.admin.web.business.service.BusinessExtendedInformationService;
import com.lionsaid.admin.web.business.service.COSService;
import com.lionsaid.admin.web.response.ResponseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author sunwei
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "/private/api/businessExtendedInformation", name = "BusinessExtendedInformationManage")
@Tag(name = "BusinessExtendedInformation管理🎭")
public class BusinessExtendedInformationController {
    private final BusinessExtendedInformationService businessExtendedInformationService;
    private final COSService cosService;


    @Operation(description = "新增BusinessExtendedInformation", summary = "新增BusinessExtendedInformation信息")
    @SysLog(value = "新增BusinessExtendedInformation")
    @PreAuthorize("hasAnyAuthority('BusinessExtendedInformationPost','administration','BusinessExtendedInformationManage','loginUser')")
    @PostMapping()
    public ResponseEntity<ResponseResult> post(@RequestBody BusinessExtendedInformationDto entity) {
        BusinessExtendedInformation sysBusinessExtendedInformation = JSONObject.parseObject(JSON.toJSONString(entity), BusinessExtendedInformation.class);
        BusinessExtendedInformation BusinessExtendedInformationInfo = businessExtendedInformationService.saveAndFlush(sysBusinessExtendedInformation);
        return ResponseEntity.ok(ResponseResult.success(BusinessExtendedInformationInfo));
    }

    @Operation(description = "删除BusinessExtendedInformation", summary = "删除BusinessExtendedInformation信息")
    @SysLog(value = "删除BusinessExtendedInformation")
    @PreAuthorize("hasAnyAuthority('BusinessExtendedInformationDelete','administration','BusinessExtendedInformationManage')")
    @DeleteMapping("")
    public ResponseEntity<ResponseResult> delete(@RequestBody String id) {
        businessExtendedInformationService.deleteAllByIdInBatch(Lists.newArrayList(id));
        return ResponseEntity.ok(ResponseResult.success(""));
    }

    @Operation(description = "更新BusinessExtendedInformationStar", summary = "更新BusinessExtendedInformationstar")
    @SysLog(value = "更新BusinessExtendedInformationStar")
    @PutMapping()
    @PreAuthorize("hasAnyAuthority('loginUser')")
    public ResponseEntity<ResponseResult> star(BusinessExtendedInformationDto entity) {
        BusinessExtendedInformation sysBusinessExtendedInformation = JSONObject.parseObject(JSON.toJSONString(entity), BusinessExtendedInformation.class);
        BusinessExtendedInformation BusinessExtendedInformationInfo = businessExtendedInformationService.saveAndFlush(sysBusinessExtendedInformation);
        return ResponseEntity.ok(ResponseResult.success(BusinessExtendedInformationInfo));
    }
}