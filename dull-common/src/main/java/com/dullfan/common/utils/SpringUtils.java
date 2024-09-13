package com.dullfan.common.utils;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import lombok.Getter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Component;

@Component
public class SpringUtils implements ApplicationContextAware {

    /**
     * -- GETTER --
     *  Gets ApplicationContext
     */
    @Getter
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (ObjectUtils.isEmpty(applicationContext)) {
            throw new ApplicationContextException("applicationContext must not be null");
        }
        SpringUtils.applicationContext = applicationContext;
    }


    /**
     * Gets bean by bean's name
     * @param name    bean's name
     * @return bean
     */
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }


    /**
     * Gets bean by bean's class(java.lang.Class)
     * @param clazz    bean Class
     * @param <T>      Class type
     * @return bean
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }


    /**
     * Gets bean by bean's class and bean's name
     * @param name    bean name
     * @param clazz   bean Class
     * @param <T>     Class type
     * @return bean
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }
}