package com.jasper.framework.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by JasperWong on 2017-07-17.
 */
public final class StringUtil {

    public static boolean isEmpty(String str){
        if(str!=null){
            str=str.trim();
        }
        return StringUtils.isEmpty(str);
    }

    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }

    public static String[] splitString(String param, String s) {
        String[] split=StringUtils.split(param,s);
        return split;
    }
}
