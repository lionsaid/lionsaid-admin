package com.lionsaid.admin.web.business.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import com.lionsaid.admin.web.annotation.SysLog;
import com.lionsaid.admin.web.business.model.dto.BusinessCardInfoDto;
import com.lionsaid.admin.web.business.model.dto.SearchDTO;
import com.lionsaid.admin.web.business.model.po.BusinessCardInfo;
import com.lionsaid.admin.web.business.service.BusinessCardInfoService;
import com.lionsaid.admin.web.business.service.BusinessExtendedInformationService;
import com.lionsaid.admin.web.business.service.COSService;
import com.lionsaid.admin.web.response.ResponseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
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
@RequestMapping(value = "/private/api/businessCard", name = "businessCardManage")
@Tag(name = "Card管理🎭")
public class BusinessCardController {
    private final BusinessCardInfoService businessCardInfoService;
    private final BusinessExtendedInformationService businessExtendedInformationService;
    private final COSService cosService;

    @Operation(description = "查询Card", summary = "通过id查询Card信息")
    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable String id) {
        log.info("get {}", id);
        return ResponseEntity.ok(ResponseResult.success(businessCardInfoService.getReferenceById(id)));
    }

    @Operation(description = "查询Card", summary = "通过id查询Card信息")
    @GetMapping("/findAll")
    @PreAuthorize("hasAnyAuthority('loginUser')")
    public ResponseEntity<ResponseResult> findAll(BusinessCardInfoDto businessCardInfoDto, Pageable pageable) {
        Page<BusinessCardInfo> page = businessCardInfoService.findByType(businessCardInfoDto.getType(), pageable);
        Map<String, List<JSONObject>> map = businessExtendedInformationService.findByJoinId(page.getContent().stream().map(BusinessCardInfo::getId).toList());
        return ResponseEntity.ok(ResponseResult.success(page.getContent().stream().map(o -> {
            JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(o));
            JSONObject content = jsonObject.getJSONObject("content");
            content.keySet().forEach(o1 -> {
                if (o1.contains("cos")) {
                    content.put(o1, cosService.getUrl(content.getString(o1)));
                }
            });
            jsonObject.put("content", content);
            jsonObject.put("extendedInfo", map.getOrDefault(o.getId(), Lists.newArrayList()));
            return jsonObject;
        }).toList()));
    }

    @Operation(description = "更新Card", summary = "更新Card信息")
    @SysLog(value = "更新Card")
    @PreAuthorize("hasAnyAuthority('businessCardPut','administration','businessCardManage')")
    @PutMapping()
    public ResponseEntity<ResponseResult> put(@RequestBody BusinessCardInfoDto entity) {
        BusinessCardInfo sysbusinessCard = JSONObject.parseObject(JSON.toJSONString(entity), BusinessCardInfo.class);
        return ResponseEntity.ok(ResponseResult.success(businessCardInfoService.saveAndFlush(sysbusinessCard)));
    }

    @Operation(description = "新增Card", summary = "新增Card信息")
    @SysLog(value = "新增Card")
    @PreAuthorize("hasAnyAuthority('businessCardPost','administration','businessCardManage')")
    @PostMapping()
    public ResponseEntity<ResponseResult> post(@RequestBody BusinessCardInfoDto entity) {
        BusinessCardInfo sysbusinessCard = JSONObject.parseObject(JSON.toJSONString(entity), BusinessCardInfo.class);
        BusinessCardInfo businessCardInfo = businessCardInfoService.saveAndFlush(sysbusinessCard);
        return ResponseEntity.ok(ResponseResult.success(businessCardInfo));
    }

    @Operation(description = "删除Card", summary = "删除Card信息")
    @SysLog(value = "删除Card")
    @PreAuthorize("hasAnyAuthority('businessCardDelete','administration','businessCardManage')")
    @DeleteMapping("")
    public ResponseEntity<ResponseResult> delete(@RequestBody String id) {
        businessCardInfoService.deleteAllByIdInBatch(Lists.newArrayList(id));
        return ResponseEntity.ok(ResponseResult.success(""));
    }

    @Operation(description = "更新CardStar", summary = "更新Cardstar")
    @SysLog(value = "更新CardStar")
    @PutMapping("/star")
    @PreAuthorize("hasAnyAuthority('loginUser')")
    public ResponseEntity<ResponseResult> star(@RequestAttribute String userId, String id) {
//        businessCardInfoService.star(userId, id);
//        List<String> stringList = businessCardInfoService.findByUserStar(SearchDTO.builder().userId(userId).build());
//        BusinessCardInfo o = businessCardInfoService.getReferenceById(id);
        JSONObject jsonObject = JSONObject.parseObject("{}");
//        jsonObject.put("backgroundImage", cosService.getUrl(jsonObject.getString("backgroundImage")));
//        jsonObject.put("userStar", stringList.contains(o.getId()));
        return ResponseEntity.ok(ResponseResult.success(jsonObject));
    }


}