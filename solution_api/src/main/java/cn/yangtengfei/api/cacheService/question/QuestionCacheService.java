package cn.yangtengfei.api.cacheService.question;

import cn.yangtengfei.cacheKey.question.QuestionCacheKey;
import cn.yangtengfei.cacheKey.question.TagCacheKey;
import cn.yangtengfei.model.question.Question;
import cn.yangtengfei.service.question.QuestionService;
import cn.yangtengfei.service.question.SolutionService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@Service
public class QuestionCacheService {

    @Autowired
    private QuestionService questionService;

    /**
     * 查询问题信息
     * @param id
     * @return
     */
    @Cacheable(value=QuestionCacheKey.QUESTION_KEY,key = "'"+ QuestionCacheKey.QUESTION_INFO_ID_KEY +"'+#id")
    public Question findById(String id){
        return  questionService.findById(id);
    }


    @Caching(put = {
            @CachePut(value = QuestionCacheKey.QUESTION_KEY, key = "'"+QuestionCacheKey.QUESTION_INFO_ID_KEY+"'+#question.id"),
            @CachePut(value = QuestionCacheKey.QUESTION_KEY, key = "'"+QuestionCacheKey.QUESTION_INFO_NAME_KEY+"'+#question.name")
    })
    public Question save(Question question) {
        question =questionService.save(question);
        return question;
    }

    /**
     * tag下问题的数量
     * @param id
     * @param deleteFlg
     * @return
     */
    @Cacheable(value=QuestionCacheKey.QUESTION_KEY,key = "'"+ TagCacheKey.TAG_QUESTION_COUNT_KEY +"'+#id")
    public long findAllCountByTagId(String id,Integer deleteFlg){
        return questionService.countByTagIdAndDeleteFlg(id,deleteFlg);
    }

    /**
     * TODO:等待消息队列
     * tag下问题的数量
     * @param id
     * @param deleteFlg
     * @return
     */
    @CachePut(value=QuestionCacheKey.QUESTION_KEY,key = "'"+ TagCacheKey.TAG_QUESTION_COUNT_KEY +"'+#id")
    public long resetAllCountByTagId(String id,Integer deleteFlg){
        return questionService.countByTagIdAndDeleteFlg(id,deleteFlg);
    }

}
