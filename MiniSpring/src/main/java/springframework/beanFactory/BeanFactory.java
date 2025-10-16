package springframework.beanFactory;

import springframework.annotation.Autowired;
import springframework.annotation.Scope;

import java.lang.reflect.Field;
import java.util.*;

public class BeanFactory {
    private final Map<Class<?>, Object> beans = new HashMap<>();
    private final List<Class<?>> componentClasses;
    private List<BeanPostProcessor> postProcessors = new ArrayList<>();

    public BeanFactory(List<Class<?>> componentClasses) throws Exception {
        this.componentClasses = componentClasses;
        initializeBeans();
    }

    private Object createBean(Class<?> clazz) throws Exception {
        Object bean = clazz.getDeclaredConstructor().newInstance();

        injectDependencies(bean);
        return bean;
    }

    private void injectDependencies(Object bean) throws Exception {
        for (Field field : bean.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Autowired.class)) {
                Object dependency = getBean(field.getType());
                field.setAccessible(true);
                field.set(bean, dependency);
            }
        }
    }
    private void initializeBeans() throws Exception {
        for (Class<?> clazz : componentClasses) {
            if (isSingleton(clazz)) {
                Object bean = clazz.getDeclaredConstructor().newInstance();
                beans.put(clazz, bean);

                if (bean instanceof BeanPostProcessor) {
                    postProcessors.add((BeanPostProcessor) bean);
                }
            }
        }

        for (Object bean : beans.values()) {
            injectDependencies(bean);
        }

        for (Map.Entry<Class<?>, Object> entry : beans.entrySet()) {
            Object bean = entry.getValue();
            String beanName = entry.getKey().getSimpleName();

            for (BeanPostProcessor processor : postProcessors) {
                bean = processor.postProcessBeforeInitialization(bean, beanName);
            }

            if (bean instanceof InitializingBean) {
                ((InitializingBean) bean).afterPropertiesSet();
            }

            for (BeanPostProcessor processor : postProcessors) {
                bean = processor.postProcessAfterInitialization(bean, beanName);
            }
        }
    }
    private boolean isSingleton(Class<?> clazz) {
        Scope scope = clazz.getAnnotation(Scope.class);
        return scope == null || scope.value().equals("singleton");
    }

    private boolean isPrototype(Class<?> clazz) {
        Scope scope = clazz.getAnnotation(Scope.class);
        return scope != null && scope.value().equals("prototype");
    }

    public <T> T getBean(Class<T> clazz) {
        try {
            if (beans.containsKey(clazz)) {
                return (T) beans.get(clazz);
            }
            if (isPrototype(clazz)) {
                return (T) createBean(clazz);
            }

            throw new RuntimeException("Бин не найден: " + clazz.getName());
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при получении бина: " + clazz.getName(), e);
        }
    }
    public void destroyAll() {
        for (Object bean : beans.values()) {
            if (bean instanceof DisposableBean) {
                ((DisposableBean) bean).destroy();
            }
        }
    }

}
