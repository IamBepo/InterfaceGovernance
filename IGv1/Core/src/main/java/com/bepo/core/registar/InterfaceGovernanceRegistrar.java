package com.bepo.core.registar;

import com.bepo.core.aspect.ApiBlockMonitorAspect;
import com.bepo.core.runner.InterfaceGovernanceRunner;
import com.bepo.core.scheduled.ApiBlockChecker;
import org.springframework.aop.framework.autoproxy.InfrastructureAdvisorAutoProxyCreator;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;

public class InterfaceGovernanceRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        String className = importingClassMetadata.getClassName();

        try {
            Class<?> annotatedClass = Class.forName(className);
            String basePackage = annotatedClass.getPackage().getName();

            // 注册 InterfaceGovernanceRunner
            InterfaceGovernanceRunner runner = new InterfaceGovernanceRunner(basePackage);
            registry.registerBeanDefinition("interfaceGovernanceRunner",
                org.springframework.beans.factory.support.BeanDefinitionBuilder.genericBeanDefinition(InterfaceGovernanceRunner.class, () -> runner).getBeanDefinition());

            // 注册 AOP 代理
            if (!registry.containsBeanDefinition("aopAutoProxyCreator")) {
                registry.registerBeanDefinition("aopAutoProxyCreator",
                        BeanDefinitionBuilder.genericBeanDefinition(InfrastructureAdvisorAutoProxyCreator.class).getBeanDefinition());
            }

            // 注册自定义 Aspect
            registry.registerBeanDefinition("apiBlockMonitorAspect",
                    BeanDefinitionBuilder.genericBeanDefinition(ApiBlockMonitorAspect.class).getBeanDefinition());

            // 注册 Scheduled 代理
            if (!registry.containsBeanDefinition("scheduledAnnotationBeanPostProcessor")) {
                registry.registerBeanDefinition("scheduledAnnotationBeanPostProcessor",
                        BeanDefinitionBuilder.genericBeanDefinition(ScheduledAnnotationBeanPostProcessor.class).getBeanDefinition());
            }

            // 注册自定义 Scheduled
            registry.registerBeanDefinition("apiBlockChecker",
                    BeanDefinitionBuilder.genericBeanDefinition(ApiBlockChecker.class).getBeanDefinition());

        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Fail Register Bean：interfaceGovernanceRunner", e);
        }
    }
}