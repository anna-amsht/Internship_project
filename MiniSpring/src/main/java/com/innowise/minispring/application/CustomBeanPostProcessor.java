package com.innowise.minispring.application;

import com.innowise.minispring.springframework.annotation.Component;
import com.innowise.minispring.springframework.beanFactory.BeanPostProcessor;

@Component
public class CustomBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        System.out.println("BeforeInit ->  " + beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        System.out.println("AfterInit ->  " + beanName);
        return bean;
    }
}
