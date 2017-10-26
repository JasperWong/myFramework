package com.jasper.framework.service;

import com.jasper.framework.helper.DatabaseHelper;
import com.jasper.framework.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;


/**
 * Created by JasperWong on 2017-06-28.
 */
public class CustomerService {
    Logger LOGGER=LoggerFactory.getLogger(getClass());

    public List<Customer> getCustomerList(){
        String sql="SELECT * FROM customer";
        return DatabaseHelper.queryEntityList(Customer.class,sql);
    }

    public Customer getCustomer(long id){
        String sql="SELECT * FROM customer WHERE id="+id;
        return DatabaseHelper.queryEntity(Customer.class,sql);
    }

    /**
     * 创建客户
     */
    public boolean createCustomer(Map<String,Object> fieldMap){
        return DatabaseHelper.insertEntity(Customer.class,fieldMap);
    }

    /**
     * 更新客户
     */
    public boolean updateCustomer(long id,Map<String,Object> fieldMap){
        return DatabaseHelper.updateEntity(Customer.class,id,fieldMap);
    }

    public boolean deleteCustomer(long id){
        return DatabaseHelper.deleteEntity(Customer.class,id);
    }

}
