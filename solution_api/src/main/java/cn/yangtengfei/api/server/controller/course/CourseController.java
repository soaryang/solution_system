package cn.yangtengfei.api.server.controller.course;


import cn.yangtengfei.api.config.PageResultModel;
import cn.yangtengfei.api.server.controller.base.BaseController;
import cn.yangtengfei.model.question.Question;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/v1/api/course")
public class CourseController extends BaseController {

    /*@RequestMapping(value = "/page", method = RequestMethod.GET)
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
    }*/
}
