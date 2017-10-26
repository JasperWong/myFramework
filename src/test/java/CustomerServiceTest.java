import com.jasper.framework.helper.DatabaseHelper;
import com.jasper.framework.model.Customer;
import com.jasper.framework.service.CustomerService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by JasperWong on 2017-06-28.
 */
public class CustomerServiceTest {
    private final CustomerService customerService;

    public CustomerServiceTest(){
        customerService=new CustomerService();
    }
    @Before
    public void init() throws IOException {
        DatabaseHelper.executeSqlFile("sql/customer_init.sql");
    }
    @Test
    public void getCustomerListTest() throws Exception{
        List<Customer> customerList=customerService.getCustomerList();
        Assert.assertEquals(2,customerList.size());
    }

    @Test
    public void getCustomerTest() throws Exception{
        long id=1;
        Customer customer=customerService.getCustomer(id);
        Assert.assertNotNull(customer);
    }

    @Test
    public void createCustomer() throws Exception{
        Map<String,Object> fieldMap=new HashMap<String,Object>();
        fieldMap.put("name","customer100");
        fieldMap.put("contact","Jhone");
        fieldMap.put("telephone","123");
        boolean result=customerService.createCustomer(fieldMap);
        Assert.assertTrue(result);
    }

    @Test
    public void deleteCustomerTest() throws Exception{
        long id=1;
        boolean result=customerService.deleteCustomer(id);
        Assert.assertTrue(result);
    }
}
