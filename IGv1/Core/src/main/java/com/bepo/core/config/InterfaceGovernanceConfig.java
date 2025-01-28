package com.bepo.core.config;

import com.bepo.core.runner.InterfaceGovernanceRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InterfaceGovernanceConfig {
    private final ApplicationContext applicationContext;

    public InterfaceGovernanceConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public InterfaceGovernanceRunner interfaceGovernanceRunner() {
        String basePackage = applicationContext.getClass().getPackage().getName();
        return new InterfaceGovernanceRunner(basePackage);
    }
}