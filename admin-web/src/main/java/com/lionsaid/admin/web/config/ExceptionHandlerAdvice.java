package com.lionsaid.admin.web.config;

import com.lionsaid.admin.web.exception.LionSaidException;
import com.lionsaid.admin.web.response.ResponseResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * The type Exception handler advice.
 *
 * @author sunwei
 */
@Slf4j
@ControllerAdvice
@AllArgsConstructor
public class ExceptionHandlerAdvice {

    private final MessageSource messageSource;


    /**
     * Handle lion dance exception response entity.
     *
     * @param e the e
     * @return the response entity
     */
    @ExceptionHandler({LionSaidException.class})
    public ResponseEntity<ResponseResult> handleLionSaidException(LionSaidException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseResult.builder().code(e.getCode()).message(messageSource.getMessage(e.getCode() + "", null, e.getLocale())).build());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ResponseResult> handleException(Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseResult.builder().code(500).message(e.getMessage()).build());
    }
}
