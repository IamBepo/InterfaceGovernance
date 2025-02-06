package com.bepo.core.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
@Slf4j
public class ApiBlockMonitorAspect {

    private static final Map<String, Long> apiExecutionTime = new ConcurrentHashMap<>();    // 存储 API 调用时间（接口开始执行的时间）

    /**
     * 监听所有 API 方法，记录开始执行时间
     */
    @Around("@within(org.springframework.web.bind.annotation.RequestMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.GetMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PostMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PutMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public Object monitorApiExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        long startTime = System.currentTimeMillis();

        apiExecutionTime.put(methodName, startTime); // 记录开始执行的时间

        try {
            Object result = joinPoint.proceed(); // 执行原方法
            apiExecutionTime.remove(methodName); // 方法执行完成，移除记录
            return result;
        } catch (Exception e) {
            apiExecutionTime.remove(methodName);
            throw e;
        }
    }

    /**
     * 获取所有正在执行的 API 及其开始时间
     */
    public static Map<String, Long> getApiExecutionTime() {
        return apiExecutionTime;
    }
}