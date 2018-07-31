package cn.yangtengfei.api.front.controller.question;


import cn.yangtengfei.api.cacheService.question.SolutionCacheService;
import cn.yangtengfei.api.config.RestResult;
import cn.yangtengfei.api.service.dataService.question.ApiQuestionService;
import cn.yangtengfei.api.server.view.question.QuestionView;
import cn.yangtengfei.api.server.view.question.SolutionView;
import cn.yangtengfei.api.front.view.question.UserQuestionUploadView;
import cn.yangtengfei.api.front.view.question.TagFrontView;
import cn.yangtengfei.api.service.dataService.question.ApiTagService;
import cn.yangtengfei.model.question.Solution;
import cn.yangtengfei.model.question.TagQuestionRelation;
import cn.yangtengfei.service.question.TagQuestionRelationService;
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


    @Autowired
    private ApiTagService apiTagService;

    @Autowired
    private TagQuestionRelationService tagQuestionRelationService;

    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    public RestResult findById(String id){
        RestResult restResult = new RestResult();
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
        restResult.setCode("200");
        restResult.setMessage("OK");
        restResult.setData(questionView);
        return restResult;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public RestResult save(@ModelAttribute UserQuestionUploadView userQuestionUploadView, HttpServletRequest request){
        RestResult restResult = new RestResult();

        List<String> tagList = userQuestionUploadView.getTagInfo();
        List<TagFrontView> tagViews = new ArrayList<>();

        /*for(String str:tagList){
            log.info(JSON.toJSONString(JSON.parseObject(str.replace(" ",""),TagFrontView.class)));
            tagViews.add(JSON.parseObject(str.replace(" ",""),TagFrontView.class));
        }*/
        tagList.forEach(tag->tagViews.add(JSON.parseObject(tag.replace(" ",""),TagFrontView.class)));
        //保存问题信息
        String id = apiQuestionService.save(userQuestionUploadView.getName(),userQuestionUploadView.getDescribe());
        //保存关系信息.
        tagViews.forEach(tagView -> saveQuestion(tagView.getId(),id));
        restResult.setCode("200");
        restResult.setMessage("OK");
        //apiQuestionService.save(questionView);
        return restResult;
    }

    private void saveQuestion(String tagId,String questionId){

        TagQuestionRelation tagQuestionRelation = new TagQuestionRelation();
        tagQuestionRelation.setQuestionId(questionId);
        tagQuestionRelation.setTagId(tagId);
        tagQuestionRelationService.save(tagQuestionRelation);
    }
}
