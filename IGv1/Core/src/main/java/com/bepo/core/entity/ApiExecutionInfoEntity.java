package com.bepo.core.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiExecutionInfoEntity {
    private String apiName;
    private String methodName;
    private long startTime;
    private String status;
    private long duration;
}
