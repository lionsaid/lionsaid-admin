package com.lionsaid.admin.web.business.controller;

import com.lionsaid.admin.web.annotation.SysLog;
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
@RequestMapping("/menu")
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
        return ResponseEntity.ok(ResponseResult.success(""));
    }


    @PreAuthorize("hasAnyAuthority('menu_get','administration')")
    @SysLog(value = "menu_post")
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
    public ResponseEntity<ResponseResult> put(HttpServletRequest request, @RequestBody SysMenu entity) {
        return ResponseEntity.ok(ResponseResult.success(""));
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