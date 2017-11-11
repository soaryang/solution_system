package cn.yangtengfei.repository.question;


import cn.yangtengfei.model.question.Solution;
import cn.yangtengfei.model.question.TagQuestionRelation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(mongoTemplateRef = "questionMongoTemplate")
public interface TagQuestionRelationRespository extends MongoRepository<TagQuestionRelation,String> {

    public void deleteByQuestionId(String questionId);

}
