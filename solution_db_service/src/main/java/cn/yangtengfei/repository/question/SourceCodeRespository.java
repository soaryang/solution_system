package cn.yangtengfei.repository.question;

import cn.yangtengfei.model.sourceCode.SourceCodeModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(mongoTemplateRef = "questionMongoTemplate")
public interface SourceCodeRespository extends MongoRepository<SourceCodeModel,String> {

    Page<SourceCodeModel> findByDeleteFlgOrderByUpdateTimeDesc(Integer deleteFlg, Pageable pageable);

}