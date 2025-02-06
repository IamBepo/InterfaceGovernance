package com.bepo.core.runner;

import com.bepo.core.anotation.ApiClass;
import com.bepo.core.anotation.ApiMethod;
import org.springframework.boot.CommandLineRunner;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

public class InterfaceGovernanceRunner implements CommandLineRunner {
    private final String basePackage;
    public static Map<String, Set<Map<String, Object>>> apiInfo = new HashMap<>();  // 存储接口类和方法的信息

    public InterfaceGovernanceRunner(String basePackage) {
        this.basePackage = basePackage;
    }

    @Override
    public void run(String... args) {
        System.out.println("Next InterfaceScan >>> ");
        System.out.println("Scan Package Path: " + basePackage);
        InterfaceScan();

    }

    /**
     * 接口扫描主入口
     */
    private void InterfaceScan() {

        String packagePath = basePackage.replace(".", "/");

        try {
            Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources(packagePath);

            // 递归获取类名
            Set<Class<?>> classes = new HashSet<>();
            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                classes.addAll(findClasses(new File(resource.getFile()), basePackage));
            }

            // 类处理
            for (Class<?> clazz : classes) {
                // 查找带有 @ApiClass 的类
                if (clazz.isAnnotationPresent(ApiClass.class)) {
                    // 存储类信息
                    Set<Map<String, Object>> methodInfoSet = new HashSet<>();
                    apiInfo.put(clazz.getName(), methodInfoSet);

                    // 查找带有 @ApiMethod 注解的方法
                    for (Method method : clazz.getDeclaredMethods()) {
                        if (method.isAnnotationPresent(ApiMethod.class)) {
                            ApiMethod apiMethodAnnotation = method.getAnnotation(ApiMethod.class);

                            // 获取 @ApiMethod 属性
                            String methodName = method.getName();
                            String apiName = apiMethodAnnotation.name();
                            String[] apiParams = apiMethodAnnotation.params();
                            String apiHttpMethod = apiMethodAnnotation.method();
                            String apiMethodDescription = apiMethodAnnotation.description();

                            // 存储方法信息
                            Map<String, Object> methodInfo = new HashMap<>();
                            methodInfo.put("methodName", methodName);
                            methodInfo.put("apiName", apiName);
                            methodInfo.put("params", apiParams);
                            methodInfo.put("httpMethod", apiHttpMethod);
                            methodInfo.put("description", apiMethodDescription);

                            methodInfoSet.add(methodInfo);
                        }
                    }
                }
            }

            // 打印收集到的 API 信息
            System.out.println("InterfaceScan Done：");
            for (Map.Entry<String, Set<Map<String, Object>>> entry : apiInfo.entrySet()) {
                System.out.println("ClassName: " + entry.getKey());
                for (Map<String, Object> methodInfo : entry.getValue()) {
                    System.out.println("  -MethodName: " + methodInfo.get("methodName"));
                    System.out.println("    API Name: " + methodInfo.get("apiName"));
                    System.out.println("    API Params: " + String.join(", ", (String[]) methodInfo.get("params")));
                    System.out.println("    API HTTP Method: " + methodInfo.get("httpMethod"));
                    System.out.println("    API Description: " + methodInfo.get("description"));
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Scan Fail!", e);
        }


    }

    /**
     * 递归查找指定包路径下的所有类
     * @param directory    当前目录
     * @param packageName  当前包名
     * @return 查找到的类集合
     */
    private Set<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
        Set<Class<?>> classes = new HashSet<>();
        if (!directory.exists()) {
            return classes;
        }

        File[] files = directory.listFiles();
        if (files == null) {
            return classes;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                // 递归扫描子目录
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                // 加载类
                String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
                classes.add(Class.forName(className));
            }
        }
        return classes;
    }
}
