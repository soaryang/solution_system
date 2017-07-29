package cn.yangtengfei.service.question;

import cn.yangtengfei.model.question.Question;
import cn.yangtengfei.repository.question.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/5/28 0028.
 */

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public Question save(Question question){
       return questionRepository.save(question);
    }

    public void del(String id){
        Question question = findQuestionById(id);
        questionRepository.delete(question);
    }

    public Question  findQuestionById(String id){
        return questionRepository.findOne(id);
    }

    public Page<Question> findAllPage(int page, int pageSize) {
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        PageRequest pageRequest = new PageRequest(page, pageSize, sort);
        return questionRepository.findAll(pageRequest);
    }

    public Page<Question> findPageByTagId(int page, int pageSize,String typeId) {
        Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
        PageRequest pageRequest = new PageRequest(page, pageSize, sort);
        //return questionRepository.findByTagId(typeId,pageRequest);
        return questionRepository.findByTagId(typeId,pageRequest);
        //return questionRepository.findByNameLike("防火墙",pageRequest);
    }

    public Page<Question> findAll(int page, int pageSize) {
        Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
        PageRequest pageRequest = new PageRequest(page, pageSize, sort);
        //return questionRepository.findByTagId(typeId,pageRequest);
        return questionRepository.findAll(pageRequest);
        //return questionRepository.findByNameLike("防火墙",pageRequest);
    }

    public long findAllCount(){
        return questionRepository.count();
    }
}
