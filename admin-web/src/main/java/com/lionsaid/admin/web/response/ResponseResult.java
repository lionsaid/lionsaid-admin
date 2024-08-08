package com.lionsaid.admin.web.response;

import jakarta.servlet.http.HttpServletResponse;
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
    double apiVersion;
    int status;
    Object content;
    Boolean success;
    LocalDateTime timestamp;

    public static ResponseResult success(Object t) {
        return ResponseResult.builder()
                .status(HttpServletResponse.SC_OK)
                .success(true)
                .apiVersion(1.0)
                .message("ok")
                .timestamp(LocalDateTime.now())
                .content(t).build();

    }


    public static ResponseResult success(Object t, String message) {
        return ResponseResult.builder()
                .status(HttpServletResponse.SC_OK)
                .success(true)
                .apiVersion(1.0)
                .message(message)
                .timestamp(LocalDateTime.now())
                .content(t).build();

    }

    public static ResponseResult error(String message) {
        return ResponseResult.builder()
                .success(false)
                .apiVersion(1.0)
                .error(message)
                .timestamp(LocalDateTime.now())
                .content("").build();
    }
}
