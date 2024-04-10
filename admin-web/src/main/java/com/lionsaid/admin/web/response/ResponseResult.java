package com.lionsaid.admin.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseResult  {
    String message;
    int apiVersion;
    int code;
    Object content;
    Boolean success;
    LocalDateTime timestamp;

    public static ResponseResult success(Object t) {
        return ResponseResult.builder()
                .success(true)
                .apiVersion(1)
                .message("ok")
                .timestamp(LocalDateTime.now())
                .content(t).build();

    }


    public static ResponseResult success(Object t, String message) {
        return ResponseResult.builder()
                .success(true)
                .apiVersion(1)
                .message(message)
                .timestamp(LocalDateTime.now())
                .content(t).build();

    }

    public static ResponseResult error(String message) {
        return ResponseResult.builder()
                .success(false)
                .apiVersion(1)
                .message(message)
                .timestamp(LocalDateTime.now())
                .content("").build();
    }
}
