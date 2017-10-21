package cn.yangtengfei.api.controller.front.question;


import cn.yangtengfei.api.cacheService.question.SolutionCacheService;
import cn.yangtengfei.api.config.Result;
import cn.yangtengfei.api.service.dataService.question.ApiQuestionService;
import cn.yangtengfei.api.view.question.QuestionView;
import cn.yangtengfei.api.view.question.SolutionView;
import cn.yangtengfei.api.view.question.UserQuestionUploadView;
import cn.yangtengfei.model.question.Solution;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/v1/api/user/question")
public class UserQuestionController {

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

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(@ModelAttribute UserQuestionUploadView userQuestionUploadView, HttpServletRequest request){
        Result result = new Result();
        result.setCode("200");
        result.setMessage("OK");
        log.info(JSON.toJSONString(userQuestionUploadView));
        //apiQuestionService.save(questionView);
        return result;
    }
}
