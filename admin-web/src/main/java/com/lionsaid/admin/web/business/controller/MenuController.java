package com.lionsaid.admin.web.business.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.lionsaid.admin.web.annotation.SysLog;
import com.lionsaid.admin.web.business.model.dto.MenuDTO;
import com.lionsaid.admin.web.business.model.po.SysMenu;
import com.lionsaid.admin.web.business.service.MenuService;
import com.lionsaid.admin.web.response.ResponseResult;
import jakarta.servlet.http.HttpServletRequest;
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
@RequestMapping("/private/api/menu")
public class MenuController {
    private final MenuService menuService;


    /**
     * @param id
     * @return
     */
    @PreAuthorize("hasAnyAuthority('menu_get','administration')")
    @GetMapping("/{id}")
    public ResponseEntity get(HttpServletRequest request, @PathVariable Long id) {
        log.info("get {}", id);
        return ResponseEntity.ok(ResponseResult.success(menuService.getById(id)));
    }


    @PreAuthorize("hasAnyAuthority('menu_get','administration')")
    @SysLog(value = "menu_get")
    @GetMapping("/findAll")
    public ResponseEntity<ResponseResult> findAll(HttpServletRequest request) {
        return ResponseEntity.ok(ResponseResult.success(""));
    }

    /**
     * @param entity
     * @return
     */
    @SysLog(value = "menu_put")
    @PreAuthorize("hasAnyAuthority('menu_put','administration')")
    @PutMapping()
    public ResponseEntity<ResponseResult> put(HttpServletRequest request, @RequestBody MenuDTO entity) {
        SysMenu sysMenu = JSONObject.parseObject(JSON.toJSONString(entity), SysMenu.class);
        return ResponseEntity.ok(ResponseResult.success(menuService.saveAndFlush(sysMenu)));
    }

    /**
     * @param entity
     * @return
     */
    @SysLog(value = "menu_post")
    @PreAuthorize("hasAnyAuthority('menu_post','administration')")
    @PostMapping()
    public ResponseEntity<ResponseResult> post(HttpServletRequest request, @RequestBody MenuDTO entity) {
        SysMenu sysMenu = JSONObject.parseObject(JSON.toJSONString(entity), SysMenu.class);
        return ResponseEntity.ok(ResponseResult.success(menuService.saveAndFlush(sysMenu)));
    }

    /**
     * @param ids
     * @return
     */
    @SysLog(value = "menu_delete")
    @PreAuthorize("hasAnyAuthority('menu_delete','administration')")
    @DeleteMapping("")
    public ResponseEntity<ResponseResult> delete(HttpServletRequest request, @RequestBody List<String> ids) {
        return ResponseEntity.ok(ResponseResult.success(""));
    }

    @SysLog(value = "用户获取本人菜单")
    @GetMapping("/getUserMenu")
    public ResponseEntity<ResponseResult> userMenuService(HttpServletRequest request, @RequestHeader String userId) {
        return ResponseEntity.ok(ResponseResult.success(""));
    }


}