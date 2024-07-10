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
public class ResponseResult {
    String message;
    String error;
    String path;
    int apiVersion;
    int status;
    Object content;
    Boolean success;
    LocalDateTime timestamp;

    public static ResponseResult success(Object t) {
        return ResponseResult.builder()
                .status(200)
                .success(true)
                .apiVersion(1)
                .message("ok")
                .timestamp(LocalDateTime.now())
                .content(t).build();

    }


    public static ResponseResult success(Object t, String message) {
        return ResponseResult.builder()
                .status(200)
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
                .error(message)
                .timestamp(LocalDateTime.now())
                .content("").build();
    }
}
