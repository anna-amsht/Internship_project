package com.innowise.minispring.springframework.context;

import com.innowise.minispring.springframework.annotation.Component;
import com.innowise.minispring.springframework.beanFactory.BeanFactory;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;


public class MiniApplicationContext {
    private final BeanFactory beanFactory;

    public MiniApplicationContext(String basePackage) {
        try {
            List<Class<?>> componentClasses = scanPackage(basePackage);
            this.beanFactory = new BeanFactory(componentClasses);

            System.out.println("Контейнер инициализирован");
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при инициализации MiniApplicationContext", e);
        }
    }

    public <T> T getBean(Class<T> clazz) {
        return beanFactory.getBean(clazz);
    }

    public void close() {
        beanFactory.destroyAll();
    }

    private List<Class<?>> scanPackage(String basePackage) throws Exception {
        List<Class<?>> classes = new ArrayList<>();

        String path = basePackage.replace('.', '/');
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        URL resource = classLoader.getResource(path);
        if (resource == null) return classes;

        File directory = new File(URLDecoder.decode(resource.getFile()));
        File[] files = directory.listFiles();
        if (files == null)
            return classes;

        for (File file : files) {
            if (file.getName().endsWith(".class")) {
                String className = basePackage + "." + file.getName().replace(".class", "");
                Class<?> clazz = Class.forName(className);
                if (clazz.isAnnotationPresent(Component.class)) {
                    classes.add(clazz);
                }
            }
        }
        return classes;
    }
}
