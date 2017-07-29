package cn.yangtengfei.api.scheduler;

import cn.yangtengfei.api.controller.question.SolutionController;
import cn.yangtengfei.api.service.question.ApiQuestionService;
import cn.yangtengfei.api.view.question.QuestionView;
import cn.yangtengfei.model.question.Question;
import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Configuration
@EnableScheduling
public class SolutionScheduler {

    private static final Logger logger = LoggerFactory.getLogger(SolutionController.class);

    @Autowired
    private ApiQuestionService apiQuestionService;

    @Value("${file.questionCachePath}")
    private String questionCachePath;

    @Scheduled(cron = "0/60 * * * * *")
    public void createQuestionCache() {
        logger.info("createQuestionCache------------start");
        Page<Question> questionPage =  apiQuestionService.findAll(0,50);
        logger.info("questionPage:{}",questionPage.getTotalElements());
        if(questionPage.getTotalElements()!=0){
            File file = new File(questionCachePath);
            Map<String,Object> dataMap = new HashMap<String,Object>();
            dataMap.put("count",apiQuestionService.findAllCount());
            dataMap.put("questionList",apiQuestionService.findQuestionListWithTags(questionPage.getContent()));
            String fileContent = JSON.toJSONString(dataMap);
            logger.info("fileContent:{}",fileContent);
            try {
                org.apache.commons.io.FileUtils.writeStringToFile(file, fileContent, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
