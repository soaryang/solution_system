package cn.yangtengfei.repository.question;

import cn.yangtengfei.model.question.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;

@EnableMongoRepositories(mongoTemplateRef = "questionMongoTemplate")
public interface TagRepository extends MongoRepository<Tag,String> {

    public Tag findByName(String name);

    Page<Tag> findByUseStatusOrderByUpdateTimeDesc(Integer useStatus, Pageable pageable);

    Page<Tag> findByUseStatusAndNameOrderByUpdateTimeDesc(Integer useStatus,String name, Pageable pageable);

    Page<Tag> findByUseStatusAndNameLikeOrderByUpdateTimeDesc(Integer useStatus,String name, Pageable pageable);

    Page<Tag> findByUseStatusAndIdAndNameLikeOrderByUpdateTimeDesc(Integer useStatus,String id, String name, Pageable pageable);

    Page<Tag> findByUseStatusAndIdOrderByUpdateTimeDesc(Integer useStatus,String name, Pageable pageable);

    Page<Tag> findByNameLikeOrderByUpdateTimeDesc(String name, Pageable pageable);

    List<Tag> findByIdInOrderByUpdateTimeDesc(List<String> ids);
}