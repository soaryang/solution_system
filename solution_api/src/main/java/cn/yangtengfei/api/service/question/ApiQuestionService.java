package cn.yangtengfei.api.service.question;

import cn.yangtengfei.api.cacheService.question.QuestionCacheService;
import cn.yangtengfei.api.view.question.QuestionView;
import cn.yangtengfei.model.question.Question;
import cn.yangtengfei.model.question.Tag;
import cn.yangtengfei.service.question.QuestionService;
import cn.yangtengfei.service.question.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/28 0028.
 */

@Service
public class ApiQuestionService {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionCacheService questionCacheService;

    @Autowired
    private TagService tagService;


    public QuestionView save(QuestionView questionView){
        Question question = new Question();
        BeanUtils.copyProperties(questionView,question);
        question = questionService.save(question);
        questionView.setId(question.getId());
        return questionView;
    }

    public QuestionView findQuestionViewById(String id){
        Question question = questionCacheService.findById(id);
        QuestionView questionView = new QuestionView();
        if(question!=null){
            String tagId = question.getTagId();
            Tag  tag = tagService.findById(tagId);
            BeanUtils.copyProperties(question,questionView);
            questionView.setTagName(tag.getName());
        }
        return questionView;
    }


    public void del(String id){
        questionService.del(id);
    }

    public Page<Question> findAllPageByTagId(int page, int pageSize,String tagId) {
        return questionService.findPageByTagId(page,pageSize,tagId);
    }

    public Page<Question> findAll(int page, int pageSize) {
        return questionService.findAll(page,pageSize);
    }

    public long findAllCount(){
        return questionService.findAllCount();
    }

    public List<QuestionView> findQuestionListWithTags(List<Question> questionList){

        List<QuestionView> questionViewList = new ArrayList<QuestionView>();
        if(questionList==null || questionList.size()==0){
            return null;
        }
        List<String> tagStrList = new ArrayList<String>();
        for(Question question:questionList){
            tagStrList.add(question.getTagId());
        }
        List<Tag> tagList = tagService.findByIdIn(tagStrList);
        Map<String,String> tagMap = new HashMap<String,String>();
        for(Tag tag:tagList){
            tagMap.put(tag.getId(),tag.getName());
        }
        for(Question question:questionList){
            QuestionView questionView = new QuestionView();
            BeanUtils.copyProperties(question,questionView);
            questionView.setSolutionCount(questionCacheService.findSolutionCountByQuestionId(question.getId()));
            questionView.setTagName(tagMap.get(question.getTagId()));
            questionViewList.add(questionView);
        }
        return questionViewList;
    }
}
