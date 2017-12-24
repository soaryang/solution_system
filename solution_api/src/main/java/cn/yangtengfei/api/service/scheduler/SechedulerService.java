package cn.yangtengfei.api.service.scheduler;

import cn.yangtengfei.api.server.view.question.QuestionView;
import cn.yangtengfei.api.service.dataService.question.ApiQuestionCountService;
import cn.yangtengfei.api.service.dataService.question.ApiQuestionService;
import cn.yangtengfei.model.question.Question;
import cn.yangtengfei.model.question.QuestionCount;
import cn.yangtengfei.service.question.QuestionCountService;
import cn.yangtengfei.service.question.QuestionService;
import cn.yangtengfei.util.ListUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import springfox.documentation.spring.web.json.Json;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class SechedulerService {

    @Value("${file.newquestionCachePath}")
    private String newquestionCachePath;

    @Value("${file.hotquestionCachePath}")
    private String hotquestionCachePath;

    @Autowired
    private QuestionCountService questionCountService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private ApiQuestionService apiQuestionService;

    public void createHotQuestionCache() throws IOException {
        log.info("createQuestionCache------------start");
        Page<QuestionCount> questionCountPage = questionCountService.findAllOrderByFollowCount(0,50);
        log.info("questionPage:{}",questionCountPage.getTotalElements());
        if(questionCountPage.getTotalElements()!=0){
            List<QuestionCount> questionCountList = questionCountPage.getContent();
            List<String> ids = new ArrayList<String>();
            questionCountList.forEach(questionCount -> ids.add(questionCount.getId()));
            List<Question> questionList = questionService.findByIdIn(ids);
            if(ListUtils.checkListIsNotNull(questionList)){
                Map<String,Object> dataMap = new HashMap<>();
                List<QuestionView>questionViews = apiQuestionService.findQuestionListWithTags(questionList);
                dataMap.put("count",apiQuestionService.findAllCount());
                dataMap.put("questionList",apiQuestionService.findQuestionListWithTags(questionList));

                FileUtils.writeStringToFile(new File(hotquestionCachePath), JSON.toJSONString(dataMap), "UTF-8");
            }





            /*List<Question> questionList = apiQuestionService.findQuestionByIds(ids);
            File file = new File(hotquestionCachePath);
            Map<String,Object> dataMap = new HashMap<String,Object>();
            dataMap.put("count",apiQuestionService.findAllCount());
            dataMap.put("questionList",apiQuestionService.findQuestionListWithTags(questionList));
            String fileContent = JSON.toJSONString(dataMap);
            logger.info("fileContent:{}",fileContent);
            try {
                org.apache.commons.io.FileUtils.writeStringToFile(file, fileContent, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }
    }

}
