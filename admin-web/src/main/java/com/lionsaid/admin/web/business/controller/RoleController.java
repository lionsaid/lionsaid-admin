package com.lionsaid.admin.web.business.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.lionsaid.admin.web.annotation.SysLog;
import com.lionsaid.admin.web.business.model.dto.RoleDTO;
import com.lionsaid.admin.web.business.model.po.SysRole;
import com.lionsaid.admin.web.business.service.RoleService;
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
@RequestMapping("/private/api/role")
@Tag(name = "角色管理🎭")
@PreAuthorize("hasAnyAuthority('administration','roleManage')")
public class RoleController {
    private final RoleService roleService;

    @Operation(description = "查询角色", summary = "通过id查询角色信息")
    @PreAuthorize("hasAnyAuthority('roleGet')")
    @GetMapping("/{id}")
    public ResponseEntity get(HttpServletRequest request, @PathVariable String id) {
        log.info("get {}", id);
        return ResponseEntity.ok(ResponseResult.success(roleService.getById(id)));
    }

    @Operation(description = "查询角色", summary = "查询所有角色信息")
    @PreAuthorize(" hasAnyAuthority('roleGet')")
    @SysLog(value = "roleGet")
    @GetMapping("/findAll")
    public ResponseEntity<ResponseResult> findAll(HttpServletRequest request) {
        return ResponseEntity.ok(ResponseResult.success(roleService.findAll(Example.of(SysRole.builder().build()))));
    }

    @Operation(description = "更新角色", summary = "更新角色信息")
    @SysLog(value = "rolePut")
    @PreAuthorize("hasAnyAuthority('rolePut')")
    @PutMapping()
    public ResponseEntity<ResponseResult> put(HttpServletRequest request, @RequestBody RoleDTO entity) {
        SysRole SysRole = JSONObject.parseObject(JSON.toJSONString(entity), SysRole.class);
        return ResponseEntity.ok(ResponseResult.success(roleService.saveAndFlush(SysRole)));
    }

    @Operation(description = "创建角色", summary = "创建角色信息")
    @SysLog(value = "rolePost")
    @PreAuthorize("hasAnyAuthority('rolePost')")
    @PostMapping()
    public ResponseEntity<ResponseResult> post(HttpServletRequest request, @RequestBody RoleDTO entity) {
        SysRole SysRole = JSONObject.parseObject(JSON.toJSONString(entity), SysRole.class);
        return ResponseEntity.ok(ResponseResult.success(roleService.saveAndFlush(SysRole)));
    }

    @Operation(description = "删除角色", summary = "删除角色信息")
    @SysLog(value = "roleDelete")
    @PreAuthorize("hasAnyAuthority('roleDelete')")
    @DeleteMapping("")
    public ResponseEntity<ResponseResult> delete(HttpServletRequest request, @RequestBody List<String> ids) {
        roleService.deleteAllByIdInBatch(ids);
        return ResponseEntity.ok(ResponseResult.success(""));
    }

    @Operation(description = "获取用户角色", summary = "获取用户角色信息")
    @SysLog(value = "getUserRole")
    @PreAuthorize("hasAnyAuthority('roleGet')")
    @GetMapping("/getUserrole")
    public ResponseEntity<ResponseResult> getUserRole(HttpServletRequest request, String userId) {
        return ResponseEntity.ok(ResponseResult.success(roleService.getRoleJoin(List.of(userId))));
    }

    @Operation(description = "删除角色JOIN关系", summary = "删除角色JOIN关系信息")
    @SysLog(value = "deleteRoleJoin")
    @PreAuthorize("hasAnyAuthority('roleDelete')")
    @DeleteMapping("/deleteRoleJoin")
    public ResponseEntity<ResponseResult> deleteRoleJoin(HttpServletRequest request, @RequestBody List<String> ids) {
        roleService.deleteRoleJoin(ids);
        return ResponseEntity.ok(ResponseResult.success(""));
    }

    @Operation(description = "新增角色JOIN关系", summary = "新增角色JOIN关系信息")
    @SysLog(value = "postRoleJoin")
    @PreAuthorize("hasAnyAuthority('rolePost')")
    @PostMapping("/postRoleJoin")
    public ResponseEntity<ResponseResult> postRoleJoin(HttpServletRequest request, @RequestBody RoleDTO entity) {
        roleService.postRoleJoin(entity.getId(), entity.getJoinId());
        return ResponseEntity.ok(ResponseResult.success(""));
    }


}