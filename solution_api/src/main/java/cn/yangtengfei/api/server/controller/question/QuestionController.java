package cn.yangtengfei.api.server.controller.question;

import cn.yangtengfei.api.config.PageResultModel;
import cn.yangtengfei.api.config.RestResult;
import cn.yangtengfei.api.server.controller.base.BaseController;
import cn.yangtengfei.api.service.dataService.question.ApiQuestionService;
import cn.yangtengfei.api.server.view.question.QuestionView;
import cn.yangtengfei.api.service.scheduler.SechedulerService;
import cn.yangtengfei.model.question.Question;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Administrator on 2017/5/28 0028.
 */

@Slf4j
@RestController
@RequestMapping(value = "/v1/api/admin/question")
public class QuestionController extends BaseController {

    @Autowired
    private ApiQuestionService apiQuestionService;


    @Autowired
    private SechedulerService sechedulerService;

    @RequestMapping(value = "/newQuestion", method = RequestMethod.GET)
    public RestResult newQuestion(){
        RestResult restResult = new RestResult();
        sechedulerService.createNewQuestionCache();
        restResult.setCode("200");
        return restResult;
    }

    @RequestMapping(value = "/hostQuestion", method = RequestMethod.GET)
    public RestResult hostQuestion() throws IOException {
        RestResult restResult = new RestResult();
        sechedulerService.createHotQuestionCache();
        restResult.setCode("200");
        return restResult;
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public PageResultModel findAll(Integer pageNumber , Integer pageSize, String tagId){
        log.info("===========================================");
        PageResultModel pageResultModel = new PageResultModel();
        Page<Question> questionPage = null;
        if(StringUtils.isEmpty(tagId)){
            questionPage = apiQuestionService.findAll(pageNumber-1,pageSize);
        }else{
            questionPage = apiQuestionService.findAllPageByTagId(pageNumber-1,pageSize,tagId);
        }
        log.info("count:{}",questionPage.getTotalElements());
        pageResultModel.setTotal(questionPage.getTotalElements());
        pageResultModel.setRows(apiQuestionService.findQuestionListWithTags(questionPage.getContent()));
        return  pageResultModel;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public RestResult save(@ModelAttribute QuestionView questionView, HttpServletRequest request){
        RestResult restResult = new RestResult();
        restResult.setCode("200");
        restResult.setMessage("OK");
        apiQuestionService.save(questionView);
        return restResult;
    }
    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    public RestResult findById(String id){
        RestResult restResult = new RestResult();
        restResult.setCode("200");
        restResult.setMessage("OK");
        restResult.setData(apiQuestionService.findQuestionViewById(id));
        return restResult;
    }



    @RequestMapping(value = "/del/{id}", method = RequestMethod.GET)
    public RestResult del(@PathVariable("id") String id){
        apiQuestionService.del(id);
        RestResult restResult = new RestResult();
        restResult.setCode("200");
        restResult.setMessage("OK");
        return restResult;

    }

}
