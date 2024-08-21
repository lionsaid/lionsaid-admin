package com.lionsaid.admin.web.business.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import com.lionsaid.admin.web.annotation.SysLog;
import com.lionsaid.admin.web.business.model.dto.BusinessQuestionInfoDto;
import com.lionsaid.admin.web.business.model.po.BusinessQuestionInfo;
import com.lionsaid.admin.web.business.service.AnswerTheQuestionsService;
import com.lionsaid.admin.web.response.ResponseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping(value = "/private/api/AnswerTheQuestions", name = "AnswerTheQuestionsManage")
@Tag(name = "答题管理")
public class AnswerTheQuestionsController {
    private final AnswerTheQuestionsService answerTheQuestionsService;

    @Operation(description = "查询答题", summary = "通过id查询答题信息")
    @PreAuthorize("hasAnyAuthority('AnswerTheQuestionsGet','administration','AnswerTheQuestionsManage')")
    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable String id) {
        log.info("get {}", id);
        return ResponseEntity.ok(ResponseResult.success(answerTheQuestionsService.getById(id)));
    }

    @Operation(description = "查询答题", summary = "通过id查询答题信息")
    @PreAuthorize("hasAnyAuthority('AnswerTheQuestionsGet','administration','AnswerTheQuestionsManage')")
    @SysLog(value = "查询答题")
    @GetMapping("/findAll")
    public ResponseEntity<ResponseResult> findAll(HttpServletRequest request) {
        return ResponseEntity.ok(ResponseResult.success(""));
    }

    @Operation(description = "更新答题", summary = "更新答题信息")
    @SysLog(value = "更新答题")
    @PreAuthorize("hasAnyAuthority('AnswerTheQuestionsPut','administration','AnswerTheQuestionsManage')")
    @PutMapping()
    public ResponseEntity<ResponseResult> put(@RequestBody BusinessQuestionInfoDto entity) {
        BusinessQuestionInfo sysAnswerTheQuestions = JSONObject.parseObject(JSON.toJSONString(entity), BusinessQuestionInfo.class);
        return ResponseEntity.ok(ResponseResult.success(answerTheQuestionsService.saveAndFlush(sysAnswerTheQuestions)));
    }

    @Operation(description = "新增答题", summary = "新增答题信息")
    @SysLog(value = "新增答题")
    @PreAuthorize("hasAnyAuthority('AnswerTheQuestionsPost','administration','AnswerTheQuestionsManage')")
    @PostMapping()
    public ResponseEntity<ResponseResult> post(@RequestBody BusinessQuestionInfoDto entity) {
        BusinessQuestionInfo sysAnswerTheQuestions = JSONObject.parseObject(JSON.toJSONString(entity), BusinessQuestionInfo.class);
        return ResponseEntity.ok(ResponseResult.success(answerTheQuestionsService.saveAndFlush(sysAnswerTheQuestions)));
    }

    @Operation(description = "删除答题", summary = "删除答题信息")
    @SysLog(value = "删除答题")
    @PreAuthorize("hasAnyAuthority('AnswerTheQuestionsDelete','administration','AnswerTheQuestionsManage')")
    @DeleteMapping("")
    public ResponseEntity<ResponseResult> delete(@RequestBody String id) {
        answerTheQuestionsService.deleteAllByIdInBatch(Lists.newArrayList(id));
        return ResponseEntity.ok(ResponseResult.success(""));
    }

    @Operation(description = "获取用户答题", summary = "获取用户答题")
    @SysLog(value = "获取用户答题")
    @PreAuthorize("hasAnyAuthority('AnswerTheQuestionsGet','administration','AnswerTheQuestionsManage')")
    @GetMapping("/getUserAnswerTheQuestions")
    public ResponseEntity<ResponseResult> useranswerTheQuestionsService(@RequestHeader String userId) {
        return ResponseEntity.ok(ResponseResult.success(answerTheQuestionsService.getUserAnswerTheQuestions(userId)));
    }

    @Operation(description = "删除答题关系", summary = "删除答题关系")
    @SysLog(value = "删除答题关系")
    @PreAuthorize("hasAnyAuthority('AnswerTheQuestionsDelete','administration','AnswerTheQuestionsManage')")
    @DeleteMapping("/deleteAnswerTheQuestionsJoin")
    public ResponseEntity<ResponseResult> deleteAnswerTheQuestionsJoin(@RequestBody List<String> ids) {
        answerTheQuestionsService.deleteAnswerTheQuestionsJoin(ids);
        return ResponseEntity.ok(ResponseResult.success(""));
    }

    @Operation(description = "新增答题关系", summary = "新增答题关系")
    @SysLog(value = "新增答题关系")
    @PreAuthorize("hasAnyAuthority('AnswerTheQuestionsPost','administration','AnswerTheQuestionsManage')")
    @PostMapping("/postAnswerTheQuestionsJoin")
    public ResponseEntity<ResponseResult> postAnswerTheQuestionsJoin(@RequestBody BusinessQuestionInfoDto entity) {
        answerTheQuestionsService.postAnswerTheQuestionsJoin(entity.getId(), entity.getJoinId());
        return ResponseEntity.ok(ResponseResult.success(""));
    }

    @Operation(description = "新增答题关系", summary = "新增答题关系")
    @SysLog(value = "新增答题关系")
    @PreAuthorize("hasAnyAuthority('AnswerTheQuestionsPost','administration','AnswerTheQuestionsManage')")
    @PostMapping("/star")
    public ResponseEntity<ResponseResult> star(@RequestHeader String userId, @RequestBody BusinessQuestionInfoDto entity) {
        answerTheQuestionsService.star(entity.getId(), userId);
        return ResponseEntity.ok(ResponseResult.success(""));
    }


}