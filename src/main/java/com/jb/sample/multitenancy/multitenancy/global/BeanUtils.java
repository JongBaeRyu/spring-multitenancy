package com.jb.sample.multitenancy.multitenancy.global;

import org.springframework.context.ApplicationContext;

public class BeanUtils {

    public static Object getBean(Class<?> classType) {
        ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
        return applicationContext.getBean(classType);
    }
}
