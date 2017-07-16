package cn.yangtengfei.repository.user;

import cn.yangtengfei.model.user.User;
import cn.yangtengfei.model.wechat.WechatUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(mongoTemplateRef = "userMongoTemplate")
public interface WechatRepostitory extends MongoRepository<WechatUser,String> {

    Page<WechatUser> findBySubscribeStateAndDeleteFlgOrderByUpdateTimeDesc(int subscribeState,int deleteFlg, Pageable pageable);

    WechatUser findByOpenId(String openId);
}
