package cn.yangtengfei.respository.employees;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import yangtengfei.model.EmployeesDO;

public interface EmployeesRepository extends ElasticsearchRepository<EmployeesDO,Integer> {
   
} 