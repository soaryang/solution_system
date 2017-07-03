package cn.yangtengfei.repository.question;

import cn.yangtengfei.model.question.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(mongoTemplateRef = "questionMongoTemplate")
public interface TagRepository extends MongoRepository<Tag,String> {

    public Tag findByName(String name);

    Page<Tag> findByUseStatus(Integer useStatus, Pageable pageable);

    Page<Tag> findByUseStatusAndNameLike(Integer useStatus,String name, Pageable pageable);

    Page<Tag> findByUseStatusAndIdAndNameLike(Integer useStatus,String id, String name, Pageable pageable);

    Page<Tag> findByUseStatusAndId(Integer useStatus,String name, Pageable pageable);

    Page<Tag> findByNameLike(String name, Pageable pageable);
}