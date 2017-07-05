package cn.yangtengfei.api.service.question;

import cn.yangtengfei.api.view.question.SolutionView;
import cn.yangtengfei.model.question.Solution;
import cn.yangtengfei.service.question.SolutionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/5/28 0028.
 */

@Service
public class ApiSolutionService {

    @Autowired
    private SolutionService solutionService;

    public SolutionView save(SolutionView solutionView){
        Solution solution = new Solution();
        BeanUtils.copyProperties(solutionView,solution);
        solution = solutionService.save(solution);
        solutionView.setId(solution.getId());
        return solutionView;
    }

    public SolutionView findSolutionViewById(String id){
        Solution solution = solutionService.findById(id);
        SolutionView solutionView = new SolutionView();
        BeanUtils.copyProperties(solution,solutionView);
        return solutionView;
    }

    public void del(String id){
        solutionService.del(id);
    }

    public Page<Solution> findAllPage(int page, int pageSize) {
        return solutionService.findAllPage(page,pageSize);
    }

    public Page<Solution> findAllByQuestionId(int page, int pageSize,String questionId) {
        return solutionService.findAllByQuestionId(page,pageSize,questionId);
    }



}
