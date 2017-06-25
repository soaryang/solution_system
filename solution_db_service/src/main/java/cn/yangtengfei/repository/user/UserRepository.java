package cn.yangtengfei.repository.user;

import cn.yangtengfei.model.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(mongoTemplateRef = "userMongoTemplate")
public interface UserRepository extends MongoRepository<User,String> {


    public User findByName(String name);

}