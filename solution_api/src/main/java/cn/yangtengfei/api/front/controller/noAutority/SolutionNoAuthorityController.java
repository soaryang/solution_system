package cn.yangtengfei.api.front.controller.noAutority;

import cn.yangtengfei.api.cacheService.question.SolutionCacheService;
import cn.yangtengfei.api.config.RestResult;
import cn.yangtengfei.api.server.view.question.SolutionView;
import cn.yangtengfei.model.question.Solution;
import cn.yangtengfei.util.ListUtils;
import com.alibaba.fastjson.JSON;
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
    public RestResult findById(@PathVariable("questionId") String  questionId){
        log.info("-----------------------通过问题编号:questionId:{}获取解决方案--------------------",questionId);
        RestResult restResult = new RestResult();
        List<Solution> solutionList = solutionCacheService.findByQuestionIdAndDeleteFlg(questionId,0);
        List<SolutionView> solutionViewList = new ArrayList<>();
        if(ListUtils.checkListIsNotNull(solutionList)){
            for(Solution solution:solutionList){
                SolutionView solutionView = new SolutionView();
                BeanUtils.copyProperties(solution,solutionView);
                solutionViewList.add(solutionView);
            }
        }else{
            log.info("solutionList:{}", JSON.toJSONString(solutionList));
            log.info("没有获取到解决方案信息");
        }
        restResult.setCode("200");
        restResult.setMessage("OK");
        restResult.setData(solutionViewList);
        return restResult;
    }
}
