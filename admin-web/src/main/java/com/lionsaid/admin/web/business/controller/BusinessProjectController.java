package com.lionsaid.admin.web.business.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import com.lionsaid.admin.web.annotation.SysLog;
import com.lionsaid.admin.web.business.model.dto.BusinessProjectInfoDto;
import com.lionsaid.admin.web.business.model.dto.SearchDTO;
import com.lionsaid.admin.web.business.model.po.BusinessProjectInfo;
import com.lionsaid.admin.web.business.service.BusinessProjectInfoService;
import com.lionsaid.admin.web.business.service.COSService;
import com.lionsaid.admin.web.response.ResponseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
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
    private final COSService cosService;

    @Operation(description = "查询项目", summary = "通过id查询项目信息")
    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable String id) {
        log.info("get {}", id);
        return ResponseEntity.ok(ResponseResult.success(businessProjectInfoService.getReferenceById(id)));
    }

    @Operation(description = "查询项目", summary = "通过id查询项目信息")
    @SysLog(value = "查询项目")
    @GetMapping("/findAll")
    @PreAuthorize("hasAnyAuthority('loginUser')")
    public ResponseEntity<ResponseResult> findAll(HttpServletRequest request, SearchDTO searchDTO, Pageable pageable) {
        searchDTO.getAttribute(request);
        List<String> stringList = businessProjectInfoService.findByUserStar(searchDTO);
        return ResponseEntity.ok(ResponseResult.success(businessProjectInfoService.findByUserProjectInfo(searchDTO, pageable).getContent().stream().map(o -> {
            JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(o));
            jsonObject.put("backgroundImage", cosService.getUrl(jsonObject.getString("backgroundImage")));
            jsonObject.put("userStar", stringList.contains(o.getId()));
            return jsonObject;
        }).toList()));

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
    public ResponseEntity<ResponseResult> post(@RequestAttribute String userId, @RequestBody BusinessProjectInfoDto entity) {
        BusinessProjectInfo sysbusinessProject = JSONObject.parseObject(JSON.toJSONString(entity), BusinessProjectInfo.class);
        BusinessProjectInfo businessProjectInfo = businessProjectInfoService.saveAndFlush(sysbusinessProject);
        return ResponseEntity.ok(ResponseResult.success(businessProjectInfo));
    }

    @Operation(description = "删除项目", summary = "删除项目信息")
    @SysLog(value = "删除项目")
    @PreAuthorize("hasAnyAuthority('businessProjectDelete','administration','businessProjectManage')")
    @DeleteMapping("")
    public ResponseEntity<ResponseResult> delete(@RequestBody String id) {
        businessProjectInfoService.deleteAllByIdInBatch(Lists.newArrayList(id));
        return ResponseEntity.ok(ResponseResult.success(""));
    }

    @Operation(description = "更新项目star", summary = "更新项目star")
    @SysLog(value = "更新项目star")
    @PutMapping("/star")
    @PreAuthorize("hasAnyAuthority('loginUser')")
    public ResponseEntity<ResponseResult> star(@RequestAttribute String userId, String id) {
        businessProjectInfoService.star(userId, id);
        List<String> stringList = businessProjectInfoService.findByUserStar(SearchDTO.builder().userId(userId).build());
        BusinessProjectInfo o = businessProjectInfoService.getReferenceById(id);
        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(o));
        jsonObject.put("backgroundImage", cosService.getUrl(jsonObject.getString("backgroundImage")));
        jsonObject.put("userStar", stringList.contains(o.getId()));
        return ResponseEntity.ok(ResponseResult.success(jsonObject));
    }

    @Operation(description = "删除项目JOIN关系", summary = "删除项目JOIN关系信息")
    @SysLog(value = "deleteRoleJoin")
    @PreAuthorize("hasAnyAuthority('businessProjectDelete','administration','businessProjectManage')")
    @DeleteMapping("/deleteRoleJoin")
    public ResponseEntity<ResponseResult> deleteRoleJoin(@RequestBody List<String> ids) {
        businessProjectInfoService.deletebusinessProjectJoin(ids);
        return ResponseEntity.ok(ResponseResult.success(""));
    }

    @Operation(description = "新增项目JOIN关系", summary = "新增项目JOIN关系信息")
    @SysLog(value = "postRoleJoin")
    @PreAuthorize("hasAnyAuthority('businessProjectPost','administration','businessProjectManage')")
    @PostMapping("/postRoleJoin")
    public ResponseEntity<ResponseResult> postRoleJoin(String id, String joinId) {
        businessProjectInfoService.postbusinessProjectJoin(id, joinId);
        return ResponseEntity.ok(ResponseResult.success(""));
    }


}