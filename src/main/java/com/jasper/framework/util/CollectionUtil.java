package com.jasper.framework.util;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.Collection;
import java.util.Map;

/**
 * Created by JasperWong on 2017-07-16.
 */
public final class CollectionUtil {
    public static boolean isEmpty(Collection<?> collection){
        return CollectionUtils.isEmpty(collection);
    }

    public static boolean isNotEmpty(Collection<?> collection){
        return !isEmpty(collection);
    }

    public static boolean isEmpty(Map<?,?> map){
        return MapUtils.isEmpty(map);
    }

    public static boolean isNotEmpty(Map<?,?> map){
        return !isEmpty(map);
    }
}
