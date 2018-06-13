package cn.tst;

import cn.yangtengfei.Application;
import cn.yangtengfei.respository.employees.EmployeesRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import yangtengfei.model.EmployeesDO;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ElasticTest {  
  
    @Autowired EmployeesRepository employeesRepository;


    @Test
    public void Test() {  
        EmployeesDO employeesDO = new EmployeesDO();
        employeesDO.setEmpNo(123);  
        employeesDO.setFirstName("Tom");  
        employeesDO.setLastName("jery");  
        employeesDO.setGender("M");  
          
        employeesRepository.save(employeesDO);  
    }  
}  