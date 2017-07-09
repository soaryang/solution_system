package cn.yangtengfei.api.controller.question;

import cn.yangtengfei.api.config.PageResultModel;
import cn.yangtengfei.api.config.Result;
import cn.yangtengfei.api.controller.base.BaseController;
import cn.yangtengfei.api.service.question.ApiQuestionService;
import cn.yangtengfei.api.view.question.QuestionView;
import cn.yangtengfei.model.question.Question;
import cn.yangtengfei.model.question.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017/5/28 0028.
 */

@RestController
@RequestMapping(value = "/v1/api/admin/question")
public class QuestionController extends BaseController {

    @Autowired
    private ApiQuestionService apiQuestionService;

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public PageResultModel findAll(Integer pageNumber , Integer pageSize, String tagId){
        PageResultModel pageResultModel = new PageResultModel();
        Page<Question> questionTypes = null;
        if(StringUtils.isEmpty(tagId)){
            questionTypes = apiQuestionService.findAll(pageNumber-1,pageSize);
        }else{
            questionTypes = apiQuestionService.findAllPageByTagId(pageNumber-1,pageSize,tagId);
        }
        pageResultModel.setTotal(questionTypes.getTotalElements());
        pageResultModel.setRows(apiQuestionService.findQuestionListWithTags(questionTypes.getContent()));
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
