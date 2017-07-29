package cn.yangtengfei.service.question;

import cn.yangtengfei.model.question.Solution;
import cn.yangtengfei.repository.question.SolutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/5/28 0028.
 */

@Service
public class SolutionService {

    @Autowired
    private SolutionRepository solutionRepository;

    public Solution save(Solution solution){
        return solutionRepository.save(solution);
    }

    public Solution findById(String id){
        return  solutionRepository.findOne(id);
    }

    public void del(String id){
        solutionRepository.delete(id);
    }

    public Page<Solution> findAllPage(int page, int pageSize) {
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        PageRequest pageRequest = new PageRequest(page, pageSize, sort);
        return solutionRepository.findAll(pageRequest);
    }

    public Page<Solution> findAllByQuestionId(int page, int pageSize,String questionId) {
        PageRequest pageRequest = new PageRequest(page, pageSize);
        return solutionRepository.findByQuestionIdOrderByUpdateTimeDesc(questionId,pageRequest);
    }

    public long findSolutionCountByQuestionId(String questionId){
        return solutionRepository.countByQuestionId(questionId);
    }
    public long findAllCount(){
        return solutionRepository.count();
    }



}
