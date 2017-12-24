package cn.yangtengfei.api.server.controller.question;

import cn.yangtengfei.api.config.PageResultModel;
import cn.yangtengfei.api.config.Result;
import cn.yangtengfei.api.server.controller.base.BaseController;
import cn.yangtengfei.api.service.dataService.question.ApiQuestionService;
import cn.yangtengfei.api.server.view.question.QuestionView;
import cn.yangtengfei.api.service.scheduler.SechedulerService;
import cn.yangtengfei.model.question.Question;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Administrator on 2017/5/28 0028.
 */

@RestController
@RequestMapping(value = "/v1/api/admin/question")
public class QuestionController extends BaseController {

    @Autowired
    private ApiQuestionService apiQuestionService;


    @Autowired
    private SechedulerService sechedulerService;

    @RequestMapping(value = "/newQuestion", method = RequestMethod.GET)
    public Result newQuestion(){
        Result result = new Result();
        sechedulerService.createNewQuestionCache();
        result.setCode("200");
        return  result;
    }

    @RequestMapping(value = "/hostQuestion", method = RequestMethod.GET)
    public Result hostQuestion() throws IOException {
        Result result = new Result();
        sechedulerService.createHotQuestionCache();
        result.setCode("200");
        return  result;
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public PageResultModel findAll(Integer pageNumber , Integer pageSize, String tagId){
        PageResultModel pageResultModel = new PageResultModel();
        Page<Question> questionPage = null;
        if(StringUtils.isEmpty(tagId)){
            questionPage = apiQuestionService.findAll(pageNumber-1,pageSize);
        }else{
            questionPage = apiQuestionService.findAllPageByTagId(pageNumber-1,pageSize,tagId);
        }
        pageResultModel.setTotal(questionPage.getTotalElements());
        pageResultModel.setRows(apiQuestionService.findQuestionListWithTags(questionPage.getContent()));
        return  pageResultModel;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(@ModelAttribute QuestionView questionView, HttpServletRequest request){
        Result result = new Result();
        result.setCode("200");
        result.setMessage("OK");
        apiQuestionService.save(questionView);
        return result;
    }
    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    public Result findById(String id){
        Result result = new Result();
        result.setCode("200");
        result.setMessage("OK");
        result.setData(apiQuestionService.findQuestionViewById(id));
        return result;
    }



    @RequestMapping(value = "/del/{id}", method = RequestMethod.GET)
    public Result del(@PathVariable("id") String id){
        apiQuestionService.del(id);
        Result result = new Result();
        result.setCode("200");
        result.setMessage("OK");
        return result;

    }

}
