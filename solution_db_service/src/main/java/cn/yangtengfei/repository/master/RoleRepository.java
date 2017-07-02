package cn.yangtengfei.repository.master;


import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(mongoTemplateRef = "masterMongoTemplate")
public class RoleRepository {
}
