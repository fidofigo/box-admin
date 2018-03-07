package com.ygg.admin.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextUtil implements ApplicationContextAware
{

    private static ApplicationContext context = null;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
        throws BeansException
    {
        context = applicationContext;
    }
    
    public static <T> T getBean(Class<T> clazz){
        return context.getBean(clazz);
    }
    
    public static <T> T getBean(String beanName,Class<T> clazz){
        return context.getBean(beanName,clazz);
    }
    public static <T> T getBean(String beanName){
        return (T)context.getBean(beanName);
    }
    
}
