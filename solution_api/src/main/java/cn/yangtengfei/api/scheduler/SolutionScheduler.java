package cn.yangtengfei.api.scheduler;

import cn.yangtengfei.api.cacheService.question.SolutionCacheService;
import cn.yangtengfei.api.controller.server.question.SolutionController;
import cn.yangtengfei.api.service.dataService.question.ApiQuestionCountService;
import cn.yangtengfei.api.service.dataService.question.ApiQuestionService;
import cn.yangtengfei.api.view.question.QuestionCountView;
import cn.yangtengfei.model.question.Question;
import cn.yangtengfei.model.question.QuestionCount;
import cn.yangtengfei.util.DateUtils;
import cn.yangtengfei.util.ListUtils;
import com.alibaba.fastjson.JSON;
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
import java.util.*;

@Component
@Configuration
@EnableScheduling
public class SolutionScheduler {

    private static final Logger logger = LoggerFactory.getLogger(SolutionController.class);

    @Autowired
    private ApiQuestionService apiQuestionService;

    @Autowired
    private SolutionCacheService solutionCacheService;

    @Autowired
    private ApiQuestionCountService apiQuestionCountService;

    @Value("${file.newquestionCachePath}")
    private String newquestionCachePath;

    @Value("${file.hotquestionCachePath}")
    private String hotquestionCachePath;

    @Scheduled(cron="0/30 * * * * ?")
    public void createHotQuestionCache() {
        logger.info("createQuestionCache------------start");

        Page<QuestionCount> questionCountPage = apiQuestionCountService.findAllOrderByFollowCount(0,50);
        logger.info("questionPage:{}",questionCountPage.getTotalElements());
        if(questionCountPage.getTotalElements()!=0){
            List<QuestionCount> questionCountList = questionCountPage.getContent();
            List<String> ids = new ArrayList<String>();
            questionCountList.forEach(questionCount -> ids.add(questionCount.getId()));
            List<Question> questionList = apiQuestionService.findQuestionByIds(ids);
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
            }
        }
    }

    @Scheduled(cron = "0/20 * * * * *")
    public void createNewQuestionCae() {
        logger.info("createQuestionCache------------start");
        Page<Question> questionPage =  apiQuestionService.findAll(0,50);
        logger.info("questionPage:{}",questionPage.getTotalElements());
        if(questionPage.getTotalElements()!=0){
            File file = new File(newquestionCachePath);
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

    @Scheduled(cron="0/60 * * * * ?")
    public void restQuestionSolutionCount() {
        logger.info("restQuestionSolutionCount------------start");
        long count = apiQuestionService.findAllCount();
        int pageSize = 500;
        int index = 0;
        int  page =0;
        if(count%pageSize==0){
            page  = (int)count/pageSize;
        }else{
            page  = (int)count/pageSize+1;
        }
        Date date = DateUtils.getCurrentDate();
        while (index<page){
            Page<Question> questionPage = null;
            try {
                //questionPage = apiQuestionService.findAllByUpdateTimeAfterOrderByUpdateTime(DateUtils.subDate(date,1),index,pageSize);
                questionPage = apiQuestionService.findAll(index,pageSize);
            } catch (Exception e) {
                e.printStackTrace();
            }
            List<Question> questionList = questionPage.getContent();
            Map<String,Integer> map = new HashMap<>();
            List<String> ids = new ArrayList<>();
            if(ListUtils.checkListIsNotNull(questionList)){
                questionList.forEach(question -> ids.add(question.getId()));
                questionList.forEach(question -> map.put(question.getId(),solutionCacheService.findSolutionCountByQuestionId(question.getId())));
                List<QuestionCount>questionCountList =  apiQuestionCountService.findByIdIn(ids);
                if(ListUtils.checkListIsNotNull(questionCountList)){
                    for(int i=0; i<questionCountList.size(); i++){
                        QuestionCount temp = questionCountList.get(i);
                        temp.setSolutionCount(map.get(temp.getId()));
                        questionCountList.set(i,temp);
                    }
                    questionCountList.forEach(temp->savequestionCount(temp));
                }else{
                    for(String id:ids){
                        QuestionCount questionCount = new QuestionCount();
                        questionCount.setId(id);
                        questionCount.setSolutionCount(map.get(id));
                        savequestionCount(questionCount);
                    }
                }
            }

            index++;
        }
    }

    private void savequestionCount(QuestionCount questionCount){
        QuestionCountView questionCountView = new QuestionCountView();
        BeanUtils.copyProperties(questionCount,questionCountView);
        apiQuestionCountService.save(questionCountView);
    }
}
