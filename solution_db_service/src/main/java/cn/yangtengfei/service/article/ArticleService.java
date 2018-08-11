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
    private ArticleRespository articleRespository;

    public void save(Article Article){
        articleRespository.save(Article);
    }

    public void Article(String id){
        articleRespository.findOne(id);
    }

    public List<Article> findArticleByTagId(String courseId){
        return articleRespository.findByTagId(courseId);
    }

    public Article findById(String id){
        return articleRespository.findOne(id);
    }

    public Page<Article> findByTagIdAndDeleteFlgOrderByUpdateTimeDesc(String tagId,Integer deleteFlg, int page, int pageSize){
        PageRequest pageRequest = new PageRequest(page, pageSize);
        if(!StringUtils.isEmpty(tagId)){
            return articleRespository.findByTagIdAndDeleteFlgOrderByUpdateTimeDesc(tagId,deleteFlg,pageRequest);
        }else{
            return articleRespository.findByDeleteFlgOrderByUpdateTimeDesc(deleteFlg,pageRequest);
        }
    }

    public long countByTagIdAndDeleteFlg(String tagId,Integer deleteFlg){
        return articleRespository.countByTagIdAndDeleteFlg(tagId,deleteFlg);
    }

    public void delete(String id){
        articleRespository.delete(id);
    }


}
