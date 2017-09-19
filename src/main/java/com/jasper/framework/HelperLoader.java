package com.jasper.framework;

import com.jasper.framework.helper.*;
import com.jasper.framework.util.ClassUtil;

/**
 * Created by JasperWong on 2017-07-16.
 */
public final class HelperLoader {
    public static void init(){
        Class<?>[] classList={
            ClassHelper.class,
            BeanHelper.class,
            AopHelper.class,
            IocHelper.class,
            ControllerHelper.class
        };
        for(Class<?> cls:classList){
            ClassUtil.loadClass(cls.getName(),true);
        }
    }
}
