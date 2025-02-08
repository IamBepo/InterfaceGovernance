package com.bepo.core.aspect;

import com.bepo.core.entity.ApiExecutionInfoEntity;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Slf4j
public class ApiBlockMonitorAspect {

    public static final Map<String, ApiExecutionInfoEntity> apiExecutionInfoMap = new ConcurrentHashMap<>();

    /**
     * 监听所有 API 方法，记录开始执行时间
     */
    @Around("@within(org.springframework.web.bind.annotation.RequestMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.GetMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PostMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PutMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public Object monitorApiExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();

        ApiExecutionInfoEntity apiInfo = ApiExecutionInfoEntity.builder()
                        .apiName(joinPoint.getSignature().toShortString())
                        .methodName(methodName)
                        .startTime(System.currentTimeMillis())
                        .status("运行中")
                        .build();

        apiExecutionInfoMap.put(methodName, apiInfo);

        try {
            Object result = joinPoint.proceed(); // 执行原方法
            apiExecutionInfoMap.remove(methodName); // 方法执行完成，移除记录
            return result;
        } catch (Exception e) {
            apiExecutionInfoMap.remove(methodName);
            throw e;
        }
    }
}