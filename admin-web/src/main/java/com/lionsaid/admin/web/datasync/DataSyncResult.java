package com.lionsaid.admin.web.datasync;

import com.alibaba.fastjson2.JSONObject;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.stream.Stream;
@Data
@Builder
@AllArgsConstructor
@NotNull
public class DataSyncResult {
    Stream<JSONObject> result;
    String scrollId;
    Integer page=0;
    Integer size=1000;
}
