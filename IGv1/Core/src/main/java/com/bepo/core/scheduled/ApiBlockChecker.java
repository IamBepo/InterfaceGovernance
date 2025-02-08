package com.bepo.core.scheduled;

import com.bepo.core.aspect.ApiBlockMonitorAspect;
import com.bepo.core.entity.ApiExecutionInfoEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class ApiBlockChecker {

    public static final Map<String, ApiExecutionInfoEntity> apiBlockStatus = new ConcurrentHashMap<>();

    /**
     * 每 1 秒检查是否有 API 被阻塞（超时 5 秒视为阻塞）
     */
    @Scheduled(fixedRate = 1000)
    public void checkBlockedApis() {

        long currentTime = System.currentTimeMillis();

        for (Map.Entry<String, ApiExecutionInfoEntity> entry : ApiBlockMonitorAspect.apiExecutionInfoMap.entrySet()) {
            String api = entry.getKey();
            ApiExecutionInfoEntity apiInfo = entry.getValue();
            long elapsedTime = (currentTime - apiInfo.getStartTime()) / 1000;

            apiInfo.setDuration(elapsedTime);

            if (elapsedTime >= 5) {
                apiInfo.setStatus("阻塞");
                apiBlockStatus.put(api, apiInfo);
                log.warn("API {} 已阻塞 {} 秒", api, elapsedTime);
            } else {
                apiInfo.setStatus("运行中");
            }
        }

        // 移除已经不在阻塞的 API
        apiBlockStatus.keySet().removeIf(api -> !ApiBlockMonitorAspect.apiExecutionInfoMap.containsKey(api));
    }

}
