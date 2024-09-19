package com.lionsaid.admin.web.business.controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.lionsaid.admin.web.annotation.SysLog;
import com.lionsaid.admin.web.business.model.po.BusinessCardInfo;
import com.lionsaid.admin.web.business.repository.BusinessCardInfoRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/public")
public class TestController {

    private final BusinessCardInfoRepository businessCardInfoRepository;

    @SneakyThrows
    @PostMapping("importCardInfo")
    public ResponseEntity generateVerificationCode(HttpServletRequest request, String type) {
        String json = IOUtils.toString(request.getInputStream());
        businessCardInfoRepository.saveAll(JSONArray.parseArray(json).toJavaList(JSONObject.class).stream().map(o -> BusinessCardInfo.builder().type(type).content(o.toJSONString()).build()).toList());
        return ResponseEntity.ok().build();
    }
}
