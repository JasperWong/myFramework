package com.jasper.chapter4.aspect;

import com.jasper.framework.annotation.Aspect;
import com.jasper.framework.annotation.Controller;
import com.jasper.framework.proxy.AspectProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

@Aspect(Controller.class)
public class ControllerAspect extends AspectProxy{
    private static final Logger LOGGER= LoggerFactory.getLogger(Controller.class);
    private long begin;
    @Override
    public void before(Class<?> cls, Method method, Object[] params) throws Throwable{
        LOGGER.debug("begin");
        LOGGER.debug(String.format("class: %s",cls.getName()));
        LOGGER.debug(String.format("method: %s",method.getName()));
        begin=System.currentTimeMillis();
    }
    @Override
    public void after(Class<?> cls,Method method,Object[] params,Object result) throws Throwable{
        LOGGER.debug(String.format("time: %dms",System.currentTimeMillis()-begin));
        LOGGER.debug("end");
    }
}
