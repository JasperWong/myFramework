package com.jasper.framework.helper;

import com.jasper.framework.annotation.Aspect;
import com.jasper.framework.annotation.Service;
import com.jasper.framework.proxy.AspectProxy;
import com.jasper.framework.proxy.Proxy;
import com.jasper.framework.proxy.ProxyManager;
import com.jasper.framework.proxy.TransactionProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.*;

public final class AopHelper {
    private static final Logger LOGGER=LoggerFactory.getLogger(AopHelper.class);

    static {
        try{
            Map<Class<?>,Set<Class<?>>> proxyMap=createProxyMap();
            Map<Class<?>,List<Proxy>> targetMap=creatTargetMap(proxyMap);
            for(Map.Entry<Class<?>,List<Proxy>> targetEntry:targetMap.entrySet()){
                Class<?> targetClass=targetEntry.getKey();
                List<Proxy> proxyList=targetEntry.getValue();
                Object proxy= ProxyManager.createProxy(targetClass,proxyList);
                BeanHelper.setBean(targetClass,proxy);
            }
        }catch (Exception e){
            LOGGER.error("aop failure",e);
        }
    }

    private static Set<Class<?>> createTargetClassSet(Aspect aspect) throws Exception{
        Set<Class<?>> targetClassSet=new HashSet<>();
        Class<? extends Annotation> annotation=aspect.value();
        if(annotation!=null&&!annotation.equals(Aspect.class)){
            targetClassSet.addAll(ClassHelper.getClassSetByAnnotation(annotation));
        }
        return targetClassSet;
    }

    private static Map<Class<?>,Set<Class<?>>> createProxyMap() throws Exception{
        Map<Class<?>,Set<Class<?>>> proxyMap=new HashMap<>();
        Set<Class<?>> proxyClassSet=ClassHelper.getClassSetBySuper(AspectProxy.class);
        for(Class<?> proxyClass:proxyClassSet){
            if(proxyClass.isAnnotationPresent(Aspect.class)){
                Aspect aspect=proxyClass.getAnnotation(Aspect.class);
                Set<Class<?>> targetClassSet=createTargetClassSet(aspect);
                proxyMap.put(proxyClass,targetClassSet);
            }
        }
        return proxyMap;
    }

    private static Map<Class<?>,List<Proxy>> creatTargetMap(Map<Class<?>,Set<Class<?>>> proxyMap)throws  Exception{
        Map<Class<?>,List<Proxy>> targetMap=new HashMap<>();
        for(Map.Entry<Class<?>,Set<Class<?>>> proxyEntry:proxyMap.entrySet()){
            Class<?> proxyClass=proxyEntry.getKey();
            Set<Class<?>> targetClassSet=proxyEntry.getValue();
            for(Class<?> targetClass:targetClassSet){
                Proxy proxy =(Proxy) proxyClass.newInstance();
                if(targetMap.containsKey(targetClass)){
                    targetMap.get(targetClass).add(proxy);
                }else {
                    List<Proxy> proxyList=new ArrayList<>();
                    proxyList.add(proxy);
                    targetMap.put(targetClass,proxyList);
                }
            }
        }
        return targetMap;
    }

    private static Map<Class<?>,Set<Class<?>>> createMap()throws Exception{
        Map<Class<?>,Set<Class<?>>> proxyMap=new HashMap<>();
        addAspectProxy(proxyMap);
        addTransactionProxy(proxyMap);
        return proxyMap;
    }

    private static void addAspectProxy(Map<Class<?>,Set<Class<?>>> proxyMap) throws Exception{
        Set<Class<?>> proxyClassSet=ClassHelper.getClassSetBySuper(AspectProxy.class);
        for(Class<?> proxyClass:proxyClassSet){
            if(proxyClass.isAnnotationPresent(Aspect.class)){
                Aspect aspect = proxyClass.getAnnotation(Aspect.class);
                Set<Class<?>> targetClassSet=createTargetClassSet(aspect);
                proxyMap.put(proxyClass,targetClassSet);
            }
        }
    }

    private static void addTransactionProxy(Map<Class<?>,Set<Class<?>>> proxyMap){
        Set<Class<?>> serviceClassSet=ClassHelper.getClassSetByAnnotation(Service.class);
        proxyMap.put(TransactionProxy.class,serviceClassSet);
    }

}