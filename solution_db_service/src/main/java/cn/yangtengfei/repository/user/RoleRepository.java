package cn.yangtengfei.repository.user;


import cn.yangtengfei.model.user.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;

@EnableMongoRepositories(mongoTemplateRef = "userMongoTemplate")
public interface RoleRepository extends MongoRepository<Role,String> {

    Page<Role> findByDeleteFlgOrderByUpdateTimeDesc(int deleteFlg, Pageable pageable);

    List<Role> findAllByDeleteFlgOrderByUpdateTimeDesc(int deleteFlg);
}
