package com.bepo.core.registar;

import com.bepo.core.runner.InterfaceGovernanceRunner;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class InterfaceGovernanceRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        String className = importingClassMetadata.getClassName();

        try {
            Class<?> annotatedClass = Class.forName(className);
            String basePackage = annotatedClass.getPackage().getName();

            // 创建 Bean
            InterfaceGovernanceRunner runner = new InterfaceGovernanceRunner(basePackage);
            // 注册 Bean
            registry.registerBeanDefinition("interfaceGovernanceRunner", 
                org.springframework.beans.factory.support.BeanDefinitionBuilder.genericBeanDefinition(InterfaceGovernanceRunner.class, () -> runner).getBeanDefinition());
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Fail Register Bean：interfaceGovernanceRunner", e);
        }
    }
}