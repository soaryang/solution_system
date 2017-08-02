package cn.yangtengfei.repository.question;

import cn.yangtengfei.model.question.Question;
import cn.yangtengfei.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;


@EnableMongoRepositories(mongoTemplateRef = "questionMongoTemplate")
public interface QuestionRepository extends MongoRepository<Question,String> {

    Page<Question> findByTagId(String tagId, Pageable pageable);

    Page<Question> findAllByUpdateTimeAfterOrderByUpdateTime(Date date, Pageable pageable);


    List<Question> findByIdIn(List<String> ids);


    long countByTagIdAndDeleteFlg(String tagId,String deleteFlg);



}