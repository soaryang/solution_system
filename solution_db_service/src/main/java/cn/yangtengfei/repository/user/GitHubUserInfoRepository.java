package cn.yangtengfei.repository.user;

import cn.yangtengfei.model.user.GitHubUserInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(mongoTemplateRef = "userMongoTemplate")
public interface GitHubUserInfoRepository  extends MongoRepository<GitHubUserInfo,String> {

    GitHubUserInfo findByGitHubId(String id);


    GitHubUserInfo findAllByLogin(String login);
}
