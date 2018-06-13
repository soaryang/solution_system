package cn.yangtengfei.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "cn.yangtengfei.respository.employees")
public class ElasticConfig {  
  
} 