package cn.yangtengfei.api.service.question;

import cn.yangtengfei.api.view.question.QuestionView;
import cn.yangtengfei.model.question.Question;
import cn.yangtengfei.service.question.QuestionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/5/28 0028.
 */

@Service
public class ApiQuestionService {

    @Autowired
    private QuestionService questionService;

    public QuestionView save(QuestionView questionView){
        Question question = new Question();
        BeanUtils.copyProperties(questionView,question);
        question = questionService.save(question);
        questionView.setId(question.getId());
        return questionView;
    }

    public void del(String id){
        questionService.del(id);
    }

    public Page<Question> findAllPageByTagId(int page, int pageSize,String tagId) {
        return questionService.findPageByTagId(page,pageSize,tagId);
    }
}
