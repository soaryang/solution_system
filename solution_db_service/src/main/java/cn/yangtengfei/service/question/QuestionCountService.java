package cn.yangtengfei.service.question;


import cn.yangtengfei.model.question.Question;
import cn.yangtengfei.model.question.QuestionCount;
import cn.yangtengfei.repository.question.QuestionCountRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class QuestionCountService {

    @Autowired
    private QuestionCountRespository questionCountRespository;

    public QuestionCount save(QuestionCount questionCount){
       return questionCountRespository.save(questionCount);
    }

    public Page<QuestionCount> findAll(int page, int pageSize) {
        Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
        PageRequest pageRequest = new PageRequest(page, pageSize, sort);
        return questionCountRespository.findAll(pageRequest);
    }

}
