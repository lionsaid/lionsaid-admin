package com.lionsaid.admin.web.controller;

import com.lionsaid.admin.web.annotation.SysLog;
import com.lionsaid.admin.web.response.ResponseResult;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author sunwei
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/menu")
public class MenuController {
//    private final MenuService menuService;
//    private final UserMenuService userMenuService;
//

//    /**
//     * @param id
//     * @return
//     */
// //   @PreAuthorize("hasAnyAuthority('menu_get','administration')")
//    @GetMapping("/{id}")
//    public Mono get(ServerWebExchange exchange, @PathVariable String id) {
//        log.info("get {}", id);
//        return ApiResponse.handleResponse(menuService.findById(id));
//    }
//


    //@PreAuthorize("hasAnyAuthority('menu_get','administration')")
    @SysLog(value = "menu_post")
    @GetMapping("/findAll")
    public ResponseEntity<ResponseResult> findAll(HttpServletRequest request) {
        return ResponseEntity.ok(ResponseResult.success(""));
    }

//    /**
//     * @param entity
//     * @return
//     */
//    @SysLog(value = "menu_post")
//    // @PreAuthorize("hasAnyAuthority('menu_post','administration')")
//    @PostMapping("")
//    public void post(ServerWebExchange exchange, @RequestBody Menu entity) {
//        return ApiResponse.handleResponse(menuService.save(entity));
//    }
//
//    /**
//     * @param entity
//     * @return
//     */
//    @SysLog(value = "menu_put")
//    @PreAuthorize("hasAnyAuthority('menu_put','administration')")
//    @PutMapping("")
//    public Mono<ApiResponse> put(ServerWebExchange exchange, @RequestBody Menu entity) {
//        return ApiResponse.handleResponse(menuService.save(entity));
//    }
//
//    /**
//     * @param ids
//     * @return
//     */
//    @SysLog(value = "menu_delete")
//    @PreAuthorize("hasAnyAuthority('menu_delete','administration')")
//    @DeleteMapping("")
//    public Mono<ApiResponse> delete(ServerWebExchange exchange, @RequestBody List<String> ids) {
//        return menuService.deleteAllById(ids).then(Mono.just(ApiResponse.success("success")));
//    }
//
//    @SysLog(value = "用户获取本人菜单")
//    @GetMapping("/getUserMenu")
//    public Mono<ApiResponse> userMenuService(ServerWebExchange exchange, @RequestHeader String userId) {
//        return ApiResponse.handleResponse(menuService.getUserMenu(userId));
//    }


}