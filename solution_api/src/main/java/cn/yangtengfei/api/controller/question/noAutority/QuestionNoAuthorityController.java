package cn.yangtengfei.api.controller.question.noAutority;

import cn.yangtengfei.api.cacheService.question.SolutionCacheService;
import cn.yangtengfei.api.config.Result;
import cn.yangtengfei.api.service.dataService.question.ApiQuestionService;
import cn.yangtengfei.api.view.question.QuestionView;
import cn.yangtengfei.api.view.question.SolutionView;
import cn.yangtengfei.model.question.Solution;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/api/question")
public class QuestionNoAuthorityController {

    @Autowired
    private ApiQuestionService apiQuestionService;

    @Autowired
    private SolutionCacheService solutionCacheService;

    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    public Result findById(String id){
        Result result = new Result();
        QuestionView questionView = apiQuestionService.findQuestionViewById(id);
        if(questionView!=null){
            List<Solution> solutionList = solutionCacheService.findByQuestionIdAndDeleteFlg(questionView.getId(),0);
            List<SolutionView> solutionViewList = new ArrayList<>();
            for(Solution solution:solutionList){
                SolutionView solutionView = new SolutionView();
                BeanUtils.copyProperties(solution,solutionView);
                solutionViewList.add(solutionView);
            }
            questionView.setSolutionViewList(solutionViewList);
        }
        result.setCode("200");
        result.setMessage("OK");
        result.setData(questionView);
        return result;
    }
}
