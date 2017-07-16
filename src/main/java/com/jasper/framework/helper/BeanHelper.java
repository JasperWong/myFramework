package com.jasper.framework.helper;

import com.jasper.framework.util.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by JasperWong on 2017-07-16.
 */
public class BeanHelper {
    private static final Map<Class<?>,Object> BEAN_MAP=new HashMap<Class<?>,Object>();

    static {
        Set<Class<?>> beanClassSet=ClassHelper.getBeanClassSet();
        for(Class<?> beanClass:beanClassSet){
            Object object= ReflectionUtil.newInstance(beanClass);
            BEAN_MAP.put(beanClass,object);
        }
    }

    /**
     * bean映射
     */
    public static Map<Class<?>,Object> getBeanMap(){
        return BEAN_MAP;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> cls){
        if(!BEAN_MAP.containsKey(cls)){
            throw new RuntimeException("can't get bean by class:"+cls);
        }
        return (T)BEAN_MAP.get(cls);
    }


}
