package cn.yangtengfei.repository.question;

import cn.yangtengfei.model.question.Question;
import cn.yangtengfei.model.question.Solution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;

@EnableMongoRepositories(mongoTemplateRef = "questionMongoTemplate")
public interface SolutionRepository extends MongoRepository<Solution,String> {

    Page<Solution> findByQuestionIdOrderByUpdateTimeDesc(String questionId, Pageable pageable);

    Integer countByQuestionId(String questionId);

    List<Solution> findByQuestionIdAndDeleteFlg(String questionId,int deleteFlg);

}