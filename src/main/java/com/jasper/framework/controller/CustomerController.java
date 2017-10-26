package com.jasper.framework.controller;

import com.jasper.framework.annotation.Action;
import com.jasper.framework.annotation.Controller;
import com.jasper.framework.annotation.Inject;
import com.jasper.framework.bean.Data;
import com.jasper.framework.bean.Param;
import com.jasper.framework.bean.View;
import com.jasper.framework.model.Customer;
import com.jasper.framework.service.CustomerService;

import java.util.List;
import java.util.Map;

@Controller
public class CustomerController {

    @Inject
    CustomerService customerService;
    @Action("get:/customer")
    public View index(Param param){
        List<Customer> customerList= customerService.getCustomerList();
        return new View("customer.jsp").addModel("customerList",customerList);
    }

    @Action("get:/customer_show")
    public View show(Param param){
        long id=param.getLong("id");
        Customer customer=customerService.getCustomer(id);
        return new View("customer_show.jsp").addModel("customer",customer);
    }

    @Action("get:/customer_create")
    public View create(Param param){
        return new View("customer_create.jsp");
    }

    @Action("post:/customer_create")
    public Data createSubmit(Param param){
        Map<String,Object> fieldMap=param.getFieldMap();
//        FileParam fileParam=param.getFile("photo");
        boolean result=customerService.createCustomer(fieldMap);
        return new Data(result);
    }

    @Action("get:/customer_edit")
    public View edit(Param param){
        long id=param.getLong("id");
        Customer customer=customerService.getCustomer(id);
        return new View("customer_edit.jsp").addModel("customer",customer);
    }

    @Action("put:/customer_edit")
    public Data editSubmit(Param param){
        long id =param.getLong("id");
        Map<String,Object> fieldMap=param.getFieldMap();
        boolean result=customerService.updateCustomer(id,fieldMap);
        return new Data(result);
    }

    @Action("delete:/customer_edit")
    public Data delete(Param param){
        long id=param.getLong("id");
        boolean result=customerService.deleteCustomer(id);
        return new Data(result);
    }
}
