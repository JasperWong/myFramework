package com.jasper.framework;

import com.jasper.framework.helper.BeanHelper;
import com.jasper.framework.helper.ClassHelper;
import com.jasper.framework.helper.ControllerHelper;
import com.jasper.framework.helper.IocHelper;

/**
 * Created by JasperWong on 2017-07-16.
 */
public final class HelperLoader {
    public static void init(){
        Class<?>[] classList={
            ClassHelper.class,
            BeanHelper.class,
            IocHelper.class,
            ControllerHelper.class
        };
    }
}
