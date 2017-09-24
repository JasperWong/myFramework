package com.jasper.controller;

import com.jasper.framework.annotation.Action;
import com.jasper.framework.annotation.Controller;
import com.jasper.framework.bean.Data;
import com.jasper.framework.bean.Param;
import com.jasper.framework.bean.View;

import java.util.List;
import java.util.Map;

@Controller
public class CustomerController {

    @Action("get:/customer")
    public View index(Param param){
        List<Customer> customerList=customer
    }



    @Action("post:/customer_create")
    public Data createSubmit(Param param){
        Map<String,Object> fieldMap=param.getFieldMap();
        FileParam fileParam=param.getFile("photo");
        boolean result=customerService.createCustomer(fieldMap,fileParam);
        return new Data(result);
    }
}
