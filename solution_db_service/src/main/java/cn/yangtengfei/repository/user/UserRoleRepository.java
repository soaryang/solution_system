package cn.yangtengfei.repository.user;


import cn.yangtengfei.model.user.UserRole;
import cn.yangtengfei.model.wechat.WechatUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(mongoTemplateRef = "userMongoTemplate")
public interface UserRoleRepository extends MongoRepository<UserRole,String> {

    public UserRole findByUserId(String userId);
}
