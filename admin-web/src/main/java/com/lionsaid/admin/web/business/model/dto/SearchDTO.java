package com.lionsaid.admin.web.business.model.dto;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchDTO {
    @Hidden
    public String userId;
    @Hidden
    public List<String> authorities;

    public void getAttribute(HttpServletRequest request) {
        userId = request.getAttribute("userId").toString();
        authorities = Arrays.stream(request.getAttribute("authorities").toString().split(",")).toList();
    }
}
