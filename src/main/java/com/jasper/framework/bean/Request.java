package com.jasper.framework.bean;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by JasperWong on 2017-07-16.
 */
public class Request {
    private String requesMethod;

    private String requestPath;

    public Request(String requesMethod,String requestPath){
        this.requesMethod=requesMethod;
        this.requestPath=requestPath;
    }

    public String getRequesMethod() {
        return requesMethod;
    }

    public String getRequestPath() {
        return requestPath;
    }
    @Override
    public int hashCode(){
        return HashCodeBuilder.reflectionHashCode(this);
    }
    @Override
    public boolean equals(Object object){
        return EqualsBuilder.reflectionEquals(this,object);
    }
}
