package cn.yangtengfei.repository.question;

import cn.yangtengfei.model.question.Question;
import cn.yangtengfei.model.question.QuestionCount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;

@EnableMongoRepositories(mongoTemplateRef = "questionMongoTemplate")
public interface QuestionCountRespository extends MongoRepository<QuestionCount,String> {

    public List<QuestionCount> findByIdIn(List<String> ids);

}
