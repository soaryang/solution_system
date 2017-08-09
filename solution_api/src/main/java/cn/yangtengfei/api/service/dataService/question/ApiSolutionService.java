package cn.yangtengfei.api.service.dataService.question;

import cn.yangtengfei.api.view.question.SolutionView;
import cn.yangtengfei.model.question.Solution;
import cn.yangtengfei.service.question.SolutionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/5/28 0028.
 */

@Service
public class ApiSolutionService {

    @Autowired
    private SolutionService solutionService;

    public SolutionView save(SolutionView solutionView){

        String id = solutionView.getId();
        Solution solution = new Solution();
        if(StringUtils.isEmpty(id)){

            BeanUtils.copyProperties(solutionView,solution);
            solution = solutionService.save(solution);
            solutionView.setId(solution.getId());
        }else{
            SolutionView tempSolutionVirew = findSolutionViewById(id);
            solutionView.setQuestionId(tempSolutionVirew.getQuestionId());
            BeanUtils.copyProperties(solutionView,solution);
            solutionService.save(solution);
        }
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

    public List<Solution> findSolutionViewList(String questionId,int deleteFlg){
        return solutionService.findByQuestionIdAndDeleteFlg(questionId,deleteFlg);
    }



}
