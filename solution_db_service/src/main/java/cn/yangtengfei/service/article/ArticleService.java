package cn.yangtengfei.service.article;

import cn.yangtengfei.model.course.Article;
import cn.yangtengfei.repository.article.ArticleRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleRespository ArticleRespository;

    public void save(Article Article){
        ArticleRespository.save(Article);
    }

    public void Article(String id){
        ArticleRespository.findOne(id);
    }

    public List<Article> findArticleByTagId(String courseId){
        return ArticleRespository.findByTagId(courseId);
    }

    public Article findById(String id){
        return ArticleRespository.findOne(id);
    }

    public Page<Article> findByTagIdAndDeleteFlgOrderByUpdateTimeDesc(String articleId,Integer deleteFlg, int page, int pageSize){
        PageRequest pageRequest = new PageRequest(page, pageSize);
        if(!StringUtils.isEmpty(articleId)){
            return ArticleRespository.findByTagIdAndDeleteFlgOrderByUpdateTimeDesc(articleId,deleteFlg,pageRequest);
        }else{
            return ArticleRespository.findByDeleteFlgOrderByUpdateTimeDesc(deleteFlg,pageRequest);
        }
    }


}
