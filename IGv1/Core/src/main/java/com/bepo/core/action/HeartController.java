package com.bepo.core.action;

import com.bepo.core.constants.ApiBlockChecker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/heartbeat")
public class HeartController {
    private final ApiBlockChecker apiBlockChecker;

    public HeartController(ApiBlockChecker apiBlockChecker) {
        this.apiBlockChecker = apiBlockChecker;
    }

    @GetMapping
    public Map<String, String> getBlockedApis() {
        return apiBlockChecker.getBlockedApis();
    }
}
