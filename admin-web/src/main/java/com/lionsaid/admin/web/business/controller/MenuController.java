package com.lionsaid.admin.web.business.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.lionsaid.admin.web.annotation.SysLog;
import com.lionsaid.admin.web.business.model.dto.MenuDTO;
import com.lionsaid.admin.web.business.model.po.SysMenu;
import com.lionsaid.admin.web.business.service.MenuService;
import com.lionsaid.admin.web.response.ResponseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author sunwei
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "/private/api/menu",name = "menuManage")
@Tag(name = "菜单管理🎭")
public class MenuController {
    private final MenuService menuService;

    @Operation(description = "查询菜单", summary = "通过id查询菜单信息")
    @PreAuthorize("hasAnyAuthority('menuGet','administration','menuManage')")
    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable String id) {
        log.info("get {}", id);
        return ResponseEntity.ok(ResponseResult.success(menuService.getById(id)));
    }

    @Operation(description = "查询菜单", summary = "通过id查询菜单信息")
    @PreAuthorize("hasAnyAuthority('menuGet','administration','menuManage')")
    @SysLog(value = "查询菜单")
    @GetMapping("/findAll")
    public ResponseEntity<ResponseResult> findAll(HttpServletRequest request) {
        return ResponseEntity.ok(ResponseResult.success(""));
    }

    @Operation(description = "更新菜单", summary = "更新菜单信息")
    @SysLog(value = "更新菜单")
    @PreAuthorize("hasAnyAuthority('menuPut','administration','menuManage')")
    @PutMapping()
    public ResponseEntity<ResponseResult> put(@RequestBody MenuDTO entity) {
        SysMenu sysMenu = JSONObject.parseObject(JSON.toJSONString(entity), SysMenu.class);
        return ResponseEntity.ok(ResponseResult.success(menuService.saveAndFlush(sysMenu)));
    }

    @Operation(description = "新增菜单", summary = "新增菜单信息")
    @SysLog(value = "新增菜单")
    @PreAuthorize("hasAnyAuthority('menuPost','administration','menuManage')")
    @PostMapping()
    public ResponseEntity<ResponseResult> post(@RequestBody MenuDTO entity) {
        SysMenu sysMenu = JSONObject.parseObject(JSON.toJSONString(entity), SysMenu.class);
        return ResponseEntity.ok(ResponseResult.success(menuService.saveAndFlush(sysMenu)));
    }

    @Operation(description = "删除菜单", summary = "删除菜单信息")
    @SysLog(value = "删除菜单")
    @PreAuthorize("hasAnyAuthority('menuDelete','administration','menuManage')")
    @DeleteMapping("")
    public ResponseEntity<ResponseResult> delete(@RequestBody String id) {
        menuService.delete(id);
        return ResponseEntity.ok(ResponseResult.success(""));
    }

    @Operation(description = "获取用户菜单", summary = "获取用户菜单")
    @SysLog(value = "获取用户菜单")
    @PreAuthorize("hasAnyAuthority('menuGet','administration','menuManage')")
    @GetMapping("/getUserMenu")
    public ResponseEntity<ResponseResult> userMenuService(@RequestAttribute String authorities) {
        return ResponseEntity.ok(ResponseResult.success(menuService.getUserMenu(Arrays.stream(authorities.split(",")).toList())));
    }

    @Operation(description = "删除菜单关系", summary = "删除菜单关系")
    @SysLog(value = "删除菜单关系")
    @PreAuthorize("hasAnyAuthority('menuDelete','administration','menuManage')")
    @DeleteMapping("/deleteMenuJoin")
    public ResponseEntity<ResponseResult> deleteMenuJoin(@RequestBody List<String> ids) {
        menuService.deleteMenuJoin(ids);
        return ResponseEntity.ok(ResponseResult.success(""));
    }

    @Operation(description = "新增菜单关系", summary = "新增菜单关系")
    @SysLog(value = "新增菜单关系")
    @PreAuthorize("hasAnyAuthority('menuPost','administration','menuManage')")
    @PostMapping("/postMenuJoin")
    public ResponseEntity<ResponseResult> postMenuJoin(@RequestBody MenuDTO entity) {
        menuService.postMenuJoin(entity.getId(), entity.getJoinId());
        return ResponseEntity.ok(ResponseResult.success(""));
    }


}