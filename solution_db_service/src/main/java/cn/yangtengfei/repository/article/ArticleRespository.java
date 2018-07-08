package cn.yangtengfei.repository.article;


import cn.yangtengfei.model.course.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;

@EnableMongoRepositories(mongoTemplateRef = "articleMongoTemplate")
public interface ArticleRespository  extends MongoRepository<Article,String> {

    List<Article> findByTagId(String courseId);

    Page<Article> findByTagIdAndDeleteFlgOrderByUpdateTimeDesc(String courseId,Integer deleteFlg, Pageable pageable);


    Page<Article> findByDeleteFlgOrderByUpdateTimeDesc(Integer deleteFlg, Pageable pageable);





}
