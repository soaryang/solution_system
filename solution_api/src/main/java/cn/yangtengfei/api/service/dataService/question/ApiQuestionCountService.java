package cn.yangtengfei.api.service.dataService.question;


import cn.yangtengfei.api.view.question.QuestionCountView;
import cn.yangtengfei.model.question.QuestionCount;
import cn.yangtengfei.service.question.QuestionCountService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<QuestionCount> findByIdIn(List<String> ids){
        return questionCountService.findByIdIn(ids);
    }

    public Page<QuestionCount> findAllOrderByUpdateTime(int page, int pageSize) {
        PageRequest pageRequest = new PageRequest(page, pageSize);
        return questionCountService.findAllOrderByUpdateTime(page,pageSize);
    }

    public Page<QuestionCount> findAllOrderByFollowCount(int page, int pageSize) {
        PageRequest pageRequest = new PageRequest(page, pageSize);
        return questionCountService.findAllOrderByFollowCount(page,pageSize);
    }

    public Page<QuestionCount> findAllOrOrderBySolutionCount(int page, int pageSize) {
        PageRequest pageRequest = new PageRequest(page, pageSize);
        return questionCountService.findAllOrOrderBySolutionCount(page,pageSize);
    }

}
