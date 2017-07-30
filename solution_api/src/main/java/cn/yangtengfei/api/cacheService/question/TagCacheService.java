package cn.yangtengfei.api.cacheService.question;

import cn.yangtengfei.cacheKey.question.QuestionCacheKey;
import cn.yangtengfei.cacheKey.question.TagCacheKey;
import cn.yangtengfei.model.question.Tag;
import cn.yangtengfei.service.question.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class TagCacheService {

    @Autowired
    private TagService tagService;


    @Cacheable(value= TagCacheKey.TAG_KEY,key = "'"+TagCacheKey.TAG_INFO_ID_KEY+"'+#id",cacheManager = "countCacheManager")
    public Tag findById(String id){ return tagService.findById(id);}


    @Cacheable(value= TagCacheKey.TAG_KEY,key = "'"+TagCacheKey.TAG_INFO_ID_KEY+"'+#id",cacheManager = "countCacheManager")
    public Tag findByName(String name){
        return tagService.findByName(name);
    }


}
