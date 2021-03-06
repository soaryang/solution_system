package cn.yangtengfei.api.cacheService.question;


import cn.yangtengfei.cacheKey.question.QuestionCacheKey;
import cn.yangtengfei.cacheKey.question.SolutionCacheKey;
import cn.yangtengfei.model.question.Solution;
import cn.yangtengfei.service.question.SolutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SolutionCacheService {

    @Autowired
    private SolutionService solutionService;


    @Cacheable(value= SolutionCacheKey.SOLUTION_KEY,key = "'"+SolutionCacheKey.SOLUTION_INFO_ID_KEY+"'+#id",cacheManager = "countCacheManager")
    public Solution findById(String id){
        return  solutionService.findById(id);
    }

    @CachePut(value= SolutionCacheKey.SOLUTION_KEY,key = "'"+SolutionCacheKey.SOLUTION_INFO_ID_KEY+"'+#solution.id",cacheManager = "countCacheManager")
    public Solution  update(Solution solution){
        return  solutionService.save(solution);
    }

    @Cacheable(value= QuestionCacheKey.QUESTION_KEY,key = "'"+QuestionCacheKey.QUESTION_SOLUTION_LiST_KEY+"'+#questionId")
    public List<Solution> findByQuestionIdAndDeleteFlg(String questionId, int deleteFlg){
        return solutionService.findByQuestionIdAndDeleteFlg(questionId,deleteFlg);
    }
    /**
     * 查询问题解决方案的数量
     * @param id
     * @return
     */
    @Cacheable(value= QuestionCacheKey.QUESTION_KEY,key = "'"+QuestionCacheKey.QUESTION_SOLUTION_COUNT_KEY+"'+#id",cacheManager = "countCacheManager")
    public Integer findSolutionCountByQuestionId(String id){
        return solutionService.findSolutionCountByQuestionId(id);
    }

    /**
     * TODO:等待消息队列
     * 重置解决方案的数量
     * @param id
     * @return
     */
    @CachePut(value= QuestionCacheKey.QUESTION_KEY,key = "'"+QuestionCacheKey.QUESTION_SOLUTION_COUNT_KEY+"'+#id",cacheManager = "countCacheManager")
    public Integer  resetSolutionCountByQuestionId(String id) {
        return solutionService.findSolutionCountByQuestionId(id);
    }




}
