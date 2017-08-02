package cn.yangtengfei.repository.question;

import cn.yangtengfei.model.question.Question;
import cn.yangtengfei.model.question.QuestionCount;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(mongoTemplateRef = "questionMongoTemplate")
public interface QuestionCountRespository extends MongoRepository<QuestionCount,String> {

}
