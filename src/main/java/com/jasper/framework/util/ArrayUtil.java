package com.jasper.framework.util;

import org.apache.commons.lang3.ArrayUtils;

/**
 * Created by JasperWong on 2017-07-16.
 */
public final class ArrayUtil {
    public static boolean isNotEmpty(Object[] array){
        return !ArrayUtils.isEmpty(array);
    }

    public static boolean isEmpty(Object[] array){
        return ArrayUtils.isEmpty(array);
    }
}
