package com.jasper.framework.bean;

import com.jasper.framework.util.CastUtil;

import java.util.Map;

/**
 * Created by JasperWong on 2017-07-16.
 */
public class Param {
    private Map<String,Object> paramMap;

    public Param(Map<String,Object> paramMap){
        this.paramMap=paramMap;
    }

    public long getLong(String name){
        return CastUtil.castLong(paramMap.get(name));
    }

    public Map<String,Object> getMap(){
        return paramMap;
    }
}
