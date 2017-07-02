package cn.yangtengfei.repository.user;

import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(mongoTemplateRef = "userMongoTemplate")
public class UserAuthorityRepository {
}
