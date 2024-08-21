package com.lionsaid.admin.web.business.controller;

import com.alibaba.fastjson2.JSONObject;
import com.lionsaid.admin.web.annotation.SysLog;
import com.lionsaid.admin.web.business.model.po.SysCOS;
import com.lionsaid.admin.web.business.service.COSService;
import com.lionsaid.admin.web.response.ResponseResult;
import com.lionsaid.admin.web.utils.LionSaidIdGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author sunwei
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "/private/api/cos", name = "menuManage")
@Tag(name = "对象存储 COS管理🎭")
public class COSController {
    private final COSService cosService;

    @Operation(description = "查询对象存储 COS", summary = "通过id查询对象存储 COS信息")
    @PreAuthorize("hasAnyAuthority('loginUser','administration','menuManage')")
    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable String id) {
        log.info("get {}", id);
        return ResponseEntity.ok(ResponseResult.success(cosService.getById(id)));
    }

    @Operation(description = "查询对象存储 COS", summary = "通过id查询对象存储 COS信息")
    @PreAuthorize("hasAnyAuthority('loginUser','administration','menuManage')")
    @SysLog(value = "查询对象存储 COS")
    @GetMapping("/findAll")
    public ResponseEntity<ResponseResult> findAll(HttpServletRequest request) {
        return ResponseEntity.ok(ResponseResult.success(""));
    }

    @Operation(description = "更新对象存储 COS", summary = "更新对象存储 COS信息")
    @SysLog(value = "更新对象存储 COS")
    @PreAuthorize("hasAnyAuthority('loginUser','administration','menuManage')")
    @PutMapping()
    public ResponseEntity<ResponseResult> put(@RequestParam String bucket,
                                              @RequestParam MultipartFile file) {
        SysCOS sysCOS = new SysCOS();
        sysCOS.setBucket(bucket);
        return ResponseEntity.ok(ResponseResult.success(cosService.saveAndFlush(sysCOS, file)));
    }

    @Operation(description = "新增对象存储 COS", summary = "新增对象存储 COS信息")
    @SysLog(value = "新增对象存储 COS")
    @PreAuthorize("hasAnyAuthority('loginUser','administration','menuManage')")
    @PostMapping()
    public ResponseEntity<ResponseResult> post(@RequestParam MultipartFile file) {
        SysCOS sysCOS = JSONObject.parseObject("{}", SysCOS.class);
        sysCOS.setId(UUID.randomUUID().toString());
        LocalDateTime now = LocalDateTime.now();
        sysCOS.setBucket("lionsaid-1305508138");
        sysCOS.setObject(now.getYear() + "/" + now.getMonth() + "/" + LionSaidIdGenerator.snowflakeId());
        return ResponseEntity.ok(ResponseResult.success(cosService.saveAndFlush(sysCOS, file)));
    }

    @Operation(description = "删除对象存储 COS", summary = "删除对象存储 COS信息")
    @SysLog(value = "删除对象存储 COS")
    @PreAuthorize("hasAnyAuthority('loginUser','administration','menuManage')")
    @DeleteMapping("")
    public ResponseEntity<ResponseResult> delete(@RequestBody String id) {
        cosService.delete(id);
        return ResponseEntity.ok(ResponseResult.success(""));
    }


}