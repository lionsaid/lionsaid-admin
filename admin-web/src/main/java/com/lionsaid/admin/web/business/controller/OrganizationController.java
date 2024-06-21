package com.lionsaid.admin.web.business.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.lionsaid.admin.web.annotation.SysLog;
import com.lionsaid.admin.web.business.model.dto.OrganizationDTO;
import com.lionsaid.admin.web.business.model.po.SysOrganization;
import com.lionsaid.admin.web.business.service.OrganizationService;
import com.lionsaid.admin.web.response.ResponseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author sunwei
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/private/api/organization")
@Tag(name = "组织管理")
@PreAuthorize("hasAnyAuthority('administration','organizationManage')")
public class OrganizationController {
    private final OrganizationService organizationService;

    @Operation(description = "查询组织", summary = "通过id查询组织信息")
    @PreAuthorize("hasAnyAuthority('organizationGet')")
    @GetMapping("/{id}")
    public ResponseEntity get(HttpServletRequest request, @PathVariable String id) {
        log.info("get {}", id);
        return ResponseEntity.ok(ResponseResult.success(organizationService.getById(id)));
    }

    @Operation(description = "查询组织", summary = "查询所有组织信息")
    @PreAuthorize(" hasAnyAuthority('organizationGet')")
    @SysLog(value = "organizationGet")
    @GetMapping("/findAll")
    public ResponseEntity<ResponseResult> findAll(HttpServletRequest request) {
        return ResponseEntity.ok(ResponseResult.success(organizationService.findAll(Example.of(SysOrganization.builder().build()))));
    }

    @Operation(description = "更新组织", summary = "更新组织信息")
    @SysLog(value = "organizationPut")
    @PreAuthorize("hasAnyAuthority('organizationPut')")
    @PutMapping()
    public ResponseEntity<ResponseResult> put(HttpServletRequest request, @RequestBody OrganizationDTO entity) {
        SysOrganization SysOrganization = JSONObject.parseObject(JSON.toJSONString(entity), SysOrganization.class);
        return ResponseEntity.ok(ResponseResult.success(organizationService.saveAndFlush(SysOrganization)));
    }

    @Operation(description = "创建组织", summary = "创建组织信息")
    @SysLog(value = "organizationPost")
    @PreAuthorize("hasAnyAuthority('organizationPost')")
    @PostMapping()
    public ResponseEntity<ResponseResult> post(HttpServletRequest request, @RequestBody OrganizationDTO entity) {
        SysOrganization SysOrganization = JSONObject.parseObject(JSON.toJSONString(entity), SysOrganization.class);
        return ResponseEntity.ok(ResponseResult.success(organizationService.saveAndFlush(SysOrganization)));
    }

    @Operation(description = "删除组织", summary = "删除组织信息")
    @SysLog(value = "organizationDelete")
    @PreAuthorize("hasAnyAuthority('organizationDelete')")
    @DeleteMapping("")
    public ResponseEntity<ResponseResult> delete(HttpServletRequest request, @RequestBody List<String> ids) {
        organizationService.deleteAllByIdInBatch(ids);
        return ResponseEntity.ok(ResponseResult.success(""));
    }

    @Operation(description = "获取用户组织", summary = "获取用户组织信息")
    @SysLog(value = "getUserOrganization")
    @PreAuthorize("hasAnyAuthority('organizationGet')")
    @GetMapping("/getUserOrganization")
    public ResponseEntity<ResponseResult> getUserorganization(HttpServletRequest request, String userId) {
        return ResponseEntity.ok(ResponseResult.success(organizationService.getOrganizationJoin(List.of(userId))));
    }


    @Operation(description = "删除组织JOIN关系", summary = "删除组织JOIN关系信息")
    @SysLog(value = "deleteOrganizationJoin")
    @PreAuthorize("hasAnyAuthority('organizationDelete')")
    @DeleteMapping("/deleteOrganizationJoin")
    public ResponseEntity<ResponseResult> deleteOrganizationJoin(HttpServletRequest request, @RequestBody List<String> ids) {
        organizationService.deleteOrganizationJoin(ids);
        return ResponseEntity.ok(ResponseResult.success(""));
    }

    @Operation(description = "新增组织JOIN关系", summary = "新增组织JOIN关系信息")
    @SysLog(value = "postOrganizationJoin")
    @PreAuthorize("hasAnyAuthority('organizationPost')")
    @PostMapping("/postOrganizationJoin")
    public ResponseEntity<ResponseResult> postOrganizationJoin(HttpServletRequest request, @RequestBody OrganizationDTO entity) {
        organizationService.postOrganizationJoin(entity.getId(), entity.getJoinId());
        return ResponseEntity.ok(ResponseResult.success(""));
    }


}