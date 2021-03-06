package cn.yangtengfei.repository.user;


import cn.yangtengfei.model.user.UserRole;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;

@EnableMongoRepositories(mongoTemplateRef = "userMongoTemplate")
public interface UserRoleRepository extends MongoRepository<UserRole,String> {

    public UserRole findByUserId(String userId);

    public List<UserRole> findByUserIdIn(List<String> ids);
}
