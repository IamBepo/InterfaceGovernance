package com.bepo.core.anotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ApiMethod {
    String name() default "";       // 接口名称
    String[] params() default {};   // 参数描述（如 ["param1:类型:描述", ...]）
    String method() default "GET";  // HTTP 方法类型
    String description() default ""; // 接口描述
}
