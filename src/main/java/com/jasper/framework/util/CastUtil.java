package com.jasper.framework.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by JasperWong on 2017-07-04.
 */
public class CastUtil {

    public static String castString(Object object){
        return CastUtil.castString(object , "");
    }

    public static String castString(Object object,String defaultValue){
        return object!=null?String.valueOf(object):defaultValue;
    }

    public static double castDouble(Object object){
        return CastUtil.castDouble(object,0);
    }

    public static double castDouble(Object object,double defaultValue){
        double doubleValue=defaultValue;
        if(object!=null){
            String strValue=castString(object);
            if(StringUtils.isNotEmpty(strValue)){
                try{
                    doubleValue=Double.parseDouble(strValue);
                }catch (NumberFormatException e){
                    doubleValue=defaultValue;
                }
            }
        }
        return doubleValue;
    }

    public static long castLong(Object object){
        return CastUtil.castLong(object,0);
    }

    public static long castLong(Object object,long defaultValue){
        long longValue=defaultValue;
        if(object!=null){
            String strVaule=castString(object);
            if(StringUtils.isNotEmpty(strVaule)){
                try{
                    longValue=Long.parseLong(strVaule);
                }catch (NumberFormatException e){
                    longValue=defaultValue;
                }
            }
        }
        return longValue;
    }

    public static int castInt(Object object){
        return castInt(object,0);
    }

    public static int castInt(Object object,int defaultValue){
        int intValue=defaultValue;
        if(object!=null){
            String strValue=castString(object);
            if(StringUtils.isNotEmpty(strValue)){
                try {
                    intValue=Integer.parseInt(strValue);
                }catch (NumberFormatException e){
                    intValue=defaultValue;
                }
            }
        }
        return intValue;
    }

    public static boolean castBoolean(Object object){
        return castBoolean(object,false);
    }

    public static boolean castBoolean(Object object,boolean defaultValue){
        boolean booleanValue=defaultValue;
        if(object!=null){
            booleanValue=Boolean.parseBoolean(castString(object));
        }
        return booleanValue;
    }
}
