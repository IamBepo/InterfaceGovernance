package com.bepo.core.runner;

import com.bepo.core.anotation.ApiClass;
import com.bepo.core.anotation.ApiMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.text.Collator;
import java.util.*;

@Slf4j
public class InterfaceGovernanceRunner implements CommandLineRunner {
    private final String basePackage;
    public static Map<String, List<Map<String, Object>>> apiInfo = new HashMap<>();  // 存储接口类和方法的信息

    public InterfaceGovernanceRunner(String basePackage) {
        this.basePackage = basePackage;
    }

    @Override
    public void run(String... args) {
        log.info("Next InterfaceScan >>> ");
        log.info("Scan Package Path: " + basePackage);
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
                    List<Map<String, Object>> methodInfoList = new ArrayList<>();
                    apiInfo.put(clazz.getAnnotation(ApiClass.class).name(), methodInfoList);

                    // 获取类上的 @RequestMapping 作为基础路径
                    StringBuffer baseUrl = new StringBuffer();
                    if (clazz.isAnnotationPresent(RequestMapping.class)) {
                        RequestMapping classMapping = clazz.getAnnotation(RequestMapping.class);
                        if (classMapping.value().length > 0) {
                            baseUrl.append(classMapping.value()[0]); // 取第一个路径
                        }
                    }

                    // 查找带有 @ApiMethod 注解的方法
                    Method[] methods = clazz.getDeclaredMethods();
                    Arrays.sort(methods, (m1, m2) -> compareMethodNames(m1.getName(), m2.getName()));
                    for (Method method : methods) {
                        if (method.isAnnotationPresent(ApiMethod.class)) {
                            ApiMethod apiMethodAnnotation = method.getAnnotation(ApiMethod.class);

                            // 获取 @ApiMethod 属性
                            String methodName = method.getName();
                            String apiName = apiMethodAnnotation.name();
                            String[] apiParams = apiMethodAnnotation.params();
                            String apiHttpMethod = apiMethodAnnotation.method();
                            String apiMethodDescription = apiMethodAnnotation.description();

                            // 获取方法上的 @xMapping
                            StringBuffer url = new StringBuffer(baseUrl);
                            url.append(getMethodMappingUrl(method));

                            // 存储方法信息
                            Map<String, Object> methodInfo = new HashMap<>();
                            methodInfo.put("methodName", methodName);
                            methodInfo.put("apiName", apiName);
                            methodInfo.put("params", apiParams);
                            methodInfo.put("httpMethod", apiHttpMethod);
                            methodInfo.put("description", apiMethodDescription);
                            methodInfo.put("url", url.toString());

                            methodInfoList.add(methodInfo);
                        }
                    }
                }
            }

            // 打印收集到的 API 信息
            log.info("InterfaceScan Done：");
            for (Map.Entry<String, List<Map<String, Object>>> entry : apiInfo.entrySet()) {
                log.info("ClassName: " + entry.getKey());
                for (Map<String, Object> methodInfo : entry.getValue()) {
                    log.info("  -MethodName: " + methodInfo.get("methodName"));
                    log.info("    API Name: " + methodInfo.get("apiName"));
                    log.info("    API Params: " + String.join(", ", (String[]) methodInfo.get("params")));
                    log.info("    API HTTP Method: " + methodInfo.get("httpMethod"));
                    log.info("    API Description: " + methodInfo.get("description"));
                    log.info("    API Url: " + methodInfo.get("url"));
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

    /**
     * 获取方法上的 URL 映射
     */
    private static String getMethodMappingUrl(Method method) {
        StringBuffer url = new StringBuffer();

        if (method.isAnnotationPresent(GetMapping.class)) {
            url.append(method.getAnnotation(GetMapping.class).value()[0]);
        } else if (method.isAnnotationPresent(PostMapping.class)) {
            url.append(method.getAnnotation(PostMapping.class).value()[0]);
        } else if (method.isAnnotationPresent(PutMapping.class)) {
            url.append(method.getAnnotation(PutMapping.class).value()[0]);
        } else if (method.isAnnotationPresent(DeleteMapping.class)) {
            url.append(method.getAnnotation(DeleteMapping.class).value()[0]);
        } else if (method.isAnnotationPresent(RequestMapping.class)) {
            url.append(method.getAnnotation(RequestMapping.class).value()[0]);
        }

        // **移除 `{}` 占位符**
        String cleanUrl = url.toString().replaceAll("\\{.*?\\}", "");

        // **移除结尾的 `/`**
        if (cleanUrl.endsWith("/")) {
            cleanUrl = cleanUrl.substring(0, cleanUrl.length() - 1);
        }

        return cleanUrl;
    }

    /**
     * 自定义排序规则：q > a > u > d，其他按字母顺序
     */
    private static int compareMethodNames(String name1, String name2) {
        List<Character> customOrder = Arrays.asList('q', 'a', 'u', 'd');

        char c1 = Character.toLowerCase(name1.charAt(0));
        char c2 = Character.toLowerCase(name2.charAt(0));

        int index1 = customOrder.indexOf(c1);
        int index2 = customOrder.indexOf(c2);

        if (index1 != -1 && index2 != -1) {
            return Integer.compare(index1, index2);
        } else if (index1 != -1) {
            return -1;
        } else if (index2 != -1) {
            return 1;
        } else {
            return Character.compare(c1, c2);
        }
    }
}
