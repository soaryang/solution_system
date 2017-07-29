package cn.yangtengfei.api.cacheService.question;

import cn.yangtengfei.model.question.Question;
import cn.yangtengfei.service.question.QuestionService;
import cn.yangtengfei.service.question.SolutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@Service
public class QuestionCacheService {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private SolutionService solutionService;

    /**
     * 查询问题信息
     * @param id
     * @return
     */
    @Cacheable(cacheNames ="questions",value="questions",key = "'question_'+#id")
    public Question findById(String id){
        return  questionService.findById(id);
    }

    /**
     * 查询问题解决方案的数量
     * @param id
     * @return
     */
    @Cacheable(cacheNames ="questions",value="questions",key = "'question_solutions_'+#id",cacheManager = "countCacheManager")
    public long findSolutionCountByQuestionId(String id){
        return solutionService.findSolutionCountByQuestionId(id);
    }

    /**
     * TODO:等待消息队列
     * 重置解决方案的数量
     * @param id
     * @return
     */
    @CachePut(cacheNames ="questions",value="questions",key = "'question_solutions_'+#id",cacheManager = "countCacheManager")
    public long resetSolutionCountByQuestionId(String id){
        return solutionService.findSolutionCountByQuestionId(id);
    }

    @Caching(put = {
            @CachePut(value = "questions", key = "'question_'+#question.id"),
            @CachePut(value = "questions", key = "'question_'+#question.name")
    })
    public Question save(Question question) {
        question =questionService.save(question);
        return question;
    }

}
