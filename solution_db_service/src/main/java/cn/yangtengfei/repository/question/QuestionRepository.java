package cn.yangtengfei.repository.question;

import cn.yangtengfei.model.question.Question;
import cn.yangtengfei.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@EnableMongoRepositories(mongoTemplateRef = "questionMongoTemplate")
public interface QuestionRepository extends MongoRepository<Question,String> {

    Page<Question> findByTagId(String tagId, Pageable pageable);


    Page<Question> findByNameLike(String Name, Pageable pageable);

}