package com.jasper.framework.bean;

import com.sun.xml.internal.ws.handler.HandlerException;

import java.lang.reflect.Method;

/**
 * Created by JasperWong on 2017-07-16.
 */
public class Handler {
    private Class<?> controllerClass;

    private Method actionMethod;

    public Handler(Class<?> controllerClass,Method actionMethod){
        this.controllerClass=controllerClass;
        this.actionMethod=actionMethod;
    }

    public Class<?> getControllerClass(){
        return controllerClass;
    }

    public Method getActionMethod(){
        return actionMethod;
    }
}
