package cn.yangtengfei.api.front.controller.noAutority;

import cn.yangtengfei.api.cacheService.question.SolutionCacheService;
import cn.yangtengfei.api.config.Result;
import cn.yangtengfei.api.server.view.question.QuestionView;
import cn.yangtengfei.api.server.view.question.SolutionView;
import cn.yangtengfei.model.question.Solution;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/12/28.
 */

@Slf4j
@RestController
@RequestMapping(value = "/v1/api/solution")
public class SolutionNoAuthorityController {

    @Autowired
    private SolutionCacheService solutionCacheService;

    @RequestMapping(value = "/findQuetionId/{questionId}", method = RequestMethod.GET)
    public Result findById(@PathVariable("questionId") String  questionId){
        Result result = new Result();
        List<Solution> solutionList = solutionCacheService.findByQuestionIdAndDeleteFlg(questionId,0);
        List<SolutionView> solutionViewList = new ArrayList<>();
        for(Solution solution:solutionList){
            SolutionView solutionView = new SolutionView();
            BeanUtils.copyProperties(solution,solutionView);
            solutionViewList.add(solutionView);
        }
        result.setCode("200");
        result.setMessage("OK");
        result.setData(solutionViewList);
        return result;
    }
}
