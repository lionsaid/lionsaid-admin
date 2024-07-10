package com.lionsaid.admin.web.business.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import com.lionsaid.admin.web.annotation.SysLog;
import com.lionsaid.admin.web.business.model.dto.BusinessProjectInfoDto;
import com.lionsaid.admin.web.business.model.po.BusinessProjectInfo;
import com.lionsaid.admin.web.business.service.BusinessProjectInfoService;
import com.lionsaid.admin.web.response.ResponseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping(value = "/private/api/businessProject", name = "businessProjectManage")
@Tag(name = "项目管理🎭")
public class BusinessProjectController {
    private final BusinessProjectInfoService businessProjectInfoService;

    @Operation(description = "查询项目", summary = "通过id查询项目信息")
    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable String id) {
        log.info("get {}", id);
        return ResponseEntity.ok(ResponseResult.success(businessProjectInfoService.getById(id)));
    }

    @Operation(description = "查询项目", summary = "通过id查询项目信息")
    @SysLog(value = "查询项目")
    @GetMapping("/findAll")
    public ResponseEntity<ResponseResult> findAll(@RequestAttribute Integer userId) {
        return ResponseEntity.ok(ResponseResult.success(""));
    }

    @Operation(description = "更新项目", summary = "更新项目信息")
    @SysLog(value = "更新项目")
    @PreAuthorize("hasAnyAuthority('businessProjectPut','administration','businessProjectManage')")
    @PutMapping()
    public ResponseEntity<ResponseResult> put(@RequestBody BusinessProjectInfoDto entity) {
        BusinessProjectInfo sysbusinessProject = JSONObject.parseObject(JSON.toJSONString(entity), BusinessProjectInfo.class);
        return ResponseEntity.ok(ResponseResult.success(businessProjectInfoService.saveAndFlush(sysbusinessProject)));
    }

    @Operation(description = "新增项目", summary = "新增项目信息")
    @SysLog(value = "新增项目")
    @PreAuthorize("hasAnyAuthority('businessProjectPost','administration','businessProjectManage')")
    @PostMapping()
    public ResponseEntity<ResponseResult> post(@RequestBody BusinessProjectInfoDto entity) {
        BusinessProjectInfo sysbusinessProject = JSONObject.parseObject(JSON.toJSONString(entity), BusinessProjectInfo.class);
        return ResponseEntity.ok(ResponseResult.success(businessProjectInfoService.saveAndFlush(sysbusinessProject)));
    }

    @Operation(description = "删除项目", summary = "删除项目信息")
    @SysLog(value = "删除项目")
    @PreAuthorize("hasAnyAuthority('businessProjectDelete','administration','businessProjectManage')")
    @DeleteMapping("")
    public ResponseEntity<ResponseResult> delete(@RequestBody String id) {
        businessProjectInfoService.deleteAllByIdInBatch(Lists.newArrayList(id));
        return ResponseEntity.ok(ResponseResult.success(""));
    }

    @Operation(description = "获取用户项目", summary = "获取用户项目")
    @SysLog(value = "获取用户项目")
    @PreAuthorize("hasAnyAuthority('businessProjectGet','administration','businessProjectManage')")
    @GetMapping("/getUserbusinessProject")
    public ResponseEntity<ResponseResult> userbusinessProjectInfoService(@RequestHeader String userId) {
        return ResponseEntity.ok(ResponseResult.success(businessProjectInfoService.getUserbusinessProject(userId)));
    }

    @Operation(description = "删除项目关系", summary = "删除项目关系")
    @SysLog(value = "删除项目关系")
    @PutMapping("/star")
    public ResponseEntity<ResponseResult> star(@RequestAttribute Integer userId, String id) {
        businessProjectInfoService.star(String.valueOf(userId), id);
        return ResponseEntity.ok(ResponseResult.success(""));
    }


}