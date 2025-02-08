package com.bepo.core.constants;

import com.bepo.core.aspect.ApiBlockMonitorAspect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class ApiBlockChecker {

    private static final Map<String, String> apiBlockStatus = new ConcurrentHashMap<>();

    /**
     * 每 3 秒检查是否有 API 被阻塞（超时 5 秒视为阻塞）
     */
    @Scheduled(fixedRate = 3000)
    public void checkBlockedApis() {
        log.info("开始检测是否有接口因高并发阻塞...");

        long currentTime = System.currentTimeMillis();
        Map<String, Long> executingApis = ApiBlockMonitorAspect.getApiExecutionTime();

        for (Map.Entry<String, Long> entry : executingApis.entrySet()) {
            String api = entry.getKey();
            long startTime = entry.getValue();
            long elapsedTime = (currentTime - startTime) / 1000; // 秒

            if (elapsedTime >= 5) { // 如果 API 运行时间 >= 5 秒，判定为阻塞
                apiBlockStatus.put(api, "阻塞, 持续时间: " + elapsedTime + " 秒");
                log.warn("API {} 已阻塞 {} 秒", api, elapsedTime);
            }
        }

        // 移除已经不在阻塞的 API
        apiBlockStatus.keySet().removeIf(api -> !executingApis.containsKey(api));
    }

    /**
     * 获取阻塞 API 状态
     */
    public Map<String, String> getBlockedApis() {
        return apiBlockStatus;
    }
}
