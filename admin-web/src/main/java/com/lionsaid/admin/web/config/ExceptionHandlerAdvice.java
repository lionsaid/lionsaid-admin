package com.lionsaid.admin.web.config;

import com.lionsaid.admin.web.business.service.DictService;
import com.lionsaid.admin.web.exception.LionSaidException;
import com.lionsaid.admin.web.response.ResponseResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.aop.Advice;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * The type Exception handler advice.
 *
 * @author sunwei
 */
@Slf4j
@ResponseBody
@ControllerAdvice
@AllArgsConstructor
public class ExceptionHandlerAdvice implements Advice {

    private final DictService dictService;

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ResponseResult> handleException(Exception e) {
       // e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseResult.builder().status(500).error(e.getMessage()).build());
    }

    /**
     * Handle lion dance exception response entity.
     *
     * @param e the e
     * @return the response entity
     */
    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ResponseResult> handleLionSaidException(LionSaidException e) {
       // e.printStackTrace();
        String message = dictService.findByDictIndexAndLanguage(String.valueOf(e.getCode()), e.getLocale().getLanguage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseResult.builder().status(e.getCode()).error(StringUtils.isEmpty(message) ? e.getMessage() : message).build());
    }


}
