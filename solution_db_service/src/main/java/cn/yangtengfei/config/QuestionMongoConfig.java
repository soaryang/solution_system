package cn.yangtengfei.config;

import com.mongodb.MongoClient;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Created by liuwei on 16/6/15.
 */
@Configuration
@EnableMongoRepositories(basePackages = "cn.yangtengfei.repository.question", mongoTemplateRef = "questionMongoTemplate")
public class QuestionMongoConfig {

    @Bean(name = "questionMongoProperties")
    @ConfigurationProperties(prefix = "spring.data.mongodb.question")
    public MongoProperties danmuMongoProperties() {
        return new MongoProperties();
    }

    @Bean(name = "questionMongoTemplate")
    public MongoTemplate danmuMongoTemplate() throws Exception {
        MongoProperties mongoProperties = danmuMongoProperties();
        MongoClient mongoClient = mongoProperties.createMongoClient(null,null);
        return new MongoTemplate(mongoClient, mongoProperties.getDatabase());
    }
}
