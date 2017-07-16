package cn.yangtengfei.repository.user;

import cn.yangtengfei.model.question.Tag;
import cn.yangtengfei.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;

@EnableMongoRepositories(mongoTemplateRef = "userMongoTemplate")
public interface UserRepository extends MongoRepository<User,String> {


    public User findByName(String name);

    Page<User> findByDeleteFlgOrderByUpdateTimeDesc(int deleteFlg,Pageable pageable);

    List<User> findByIdIn(List<String> ids);


}