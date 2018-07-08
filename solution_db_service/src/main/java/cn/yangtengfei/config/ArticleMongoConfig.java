package cn.yangtengfei.config;

import com.mongodb.MongoClient;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@Configuration
@EnableMongoRepositories(basePackages = "cn.yangtengfei.repository.article", mongoTemplateRef = "articleMongoTemplate")
public class ArticleMongoConfig {

    @Bean(name = "courseMongoProperties")
    @ConfigurationProperties(prefix = "spring.data.mongodb.article")
    public MongoProperties danmuMongoProperties() {
        return new MongoProperties();
    }

    @Bean(name = "articleMongoTemplate")
    public MongoTemplate danmuMongoTemplate() throws Exception {
        MongoProperties mongoProperties = danmuMongoProperties();
        MongoClient mongoClient = mongoProperties.createMongoClient(null,null);
        return new MongoTemplate(mongoClient, mongoProperties.getDatabase());
    }
}
