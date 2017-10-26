package com.jasper.framework.controller;

import com.jasper.framework.annotation.Action;
import com.jasper.framework.annotation.Controller;
import com.jasper.framework.bean.Data;
import com.jasper.framework.bean.FileParam;
import com.jasper.framework.bean.Param;
import com.jasper.framework.bean.View;
import com.jasper.framework.model.Customer;
import com.jasper.framework.service.CustomerService;

import java.util.List;
import java.util.Map;

@Controller
public class CustomerController {
    CustomerService customerService=new CustomerService();
    @Action("get:/customer")
    public View index(Param param){
        List<Customer> customerList= customerService.getCustomerList();
        return null;
    }



    @Action("post:/customer_create")
    public Data createSubmit(Param param){
        Map<String,Object> fieldMap=param.getFieldMap();
        FileParam fileParam=param.getFile("photo");
//        boolean result=customerService.createCustomer(fieldMap,fileParam);
//        return new Data(result);
        return null;
    }
}
