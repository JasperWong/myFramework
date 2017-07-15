package com.jasper.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by JasperWong on 2017-06-29.
 */
public class PropsUtil {
    private static final Logger logger= LoggerFactory.getLogger(PropsUtil.class);

    public static Properties loadProps(String fileName){
        Properties properties=null;
        InputStream inputStream=null;
        try{
            inputStream=Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if(inputStream==null){
                throw new FileNotFoundException(fileName+"file is not found");
            }
            properties=new Properties();
            properties.load(inputStream);
        } catch (IOException e){
        logger.error("load properties file failure",e);
        } finally {
            if(inputStream!=null){
                try{
                    inputStream.close();
                } catch (IOException e){
                    logger.error("close fail",e);
                }
            }
        }
        return properties;
    }

    public static String getString(Properties properties,String key,String defaultValue){
        String value=defaultValue;
        if(properties.containsKey(key)){
            value=properties.getProperty(key);
        }
        return value;
    }

    public static int getInt(Properties properties,String key){
        return getInt(properties,key,0);
    }

    public static int getInt(Properties properties, String key, int i) {
        int value=i;
        if(properties.containsKey(key)){
            value=CastUtil.castInt(properties.getProperty(key));
        }
        return value;
    }

    public static boolean getBoolean(Properties properties,String key){
        return getBoolean(properties,key,false);
    }

    public static boolean getBoolean(Properties properties,String key,boolean defalutValue){
        boolean booleanValue=defalutValue;
        if(properties.containsKey(key)){
            booleanValue=CastUtil.castBoolean(properties.getProperty(key));
        }
        return booleanValue;
    }




}
