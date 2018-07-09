package cn.yangtengfei.repository.question;

import cn.yangtengfei.model.question.Tag;
import cn.yangtengfei.model.question.TagTitle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@EnableMongoRepositories(mongoTemplateRef = "questionMongoTemplate")
public interface TagTitleRespository extends MongoRepository<TagTitle,String> {

    TagTitle findByTagId(String tagId);
}
