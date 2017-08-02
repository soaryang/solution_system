package cn.yangtengfei.api.service.question;


import cn.yangtengfei.api.view.question.QuestionCountView;
import cn.yangtengfei.model.question.Question;
import cn.yangtengfei.model.question.QuestionCount;
import cn.yangtengfei.service.question.QuestionCountService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ApiQuestionCountService {

    @Autowired
    private QuestionCountService questionCountService;

    public QuestionCountView save(QuestionCountView questionCountView){
        QuestionCount questionCount = new QuestionCount();
        BeanUtils.copyProperties(questionCountView,questionCount);
        questionCount = questionCountService.save(questionCount);
        BeanUtils.copyProperties(questionCount,questionCountView);
        return questionCountView;
    }

    public Page<QuestionCount> findAll(int page, int pageSize) {
        return questionCountService.findAll(page,pageSize);
    }
}
