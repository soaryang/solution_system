package cn.yangtengfei.api.scheduler;

import cn.yangtengfei.api.service.question.ApiQuestionService;
import cn.yangtengfei.api.view.question.QuestionView;
import cn.yangtengfei.model.question.Question;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@Configuration
@EnableScheduling
public class SolutionScheduler {

    @Autowired
    private ApiQuestionService apiQuestionService;

    @Value("${file.questionCachePath}")
    private String questionCachePath;

    @Scheduled(cron = "0/30 * * * * *")
    public void reportCurrentByCron() {
        Page<Question> questionPage =  apiQuestionService.findAll(0,20);
        if(questionPage.getTotalElements()!=0){
            List<Question> questionList = questionPage.getContent();
            List<QuestionView> questionViews = new ArrayList<QuestionView>();
            for(Question question:questionList){
                QuestionView questionView = new QuestionView();
                BeanUtils.copyProperties(question,questionView);
                questionViews.add(questionView);
            }
            File file = new File(questionCachePath);
            String fileContent = "";
            try {
                org.apache.commons.io.FileUtils.writeStringToFile(file, fileContent, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
