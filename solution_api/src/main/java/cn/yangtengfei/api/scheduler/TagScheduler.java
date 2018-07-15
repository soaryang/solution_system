package cn.yangtengfei.api.scheduler;

import cn.yangtengfei.api.wechat.message.Article;
import cn.yangtengfei.model.question.Tag;
import cn.yangtengfei.service.article.ArticleService;
import cn.yangtengfei.service.question.QuestionService;
import cn.yangtengfei.service.question.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;


@Slf4j
@Component
@Configuration
@EnableScheduling
public class TagScheduler {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private TagService tagService;

    @Autowired
    private ArticleService articleService;

    @Description("当前标签下的问题数量")
    @Scheduled(cron="0 0/5 * * * ?")
    public void tagQuestionCount() {
        log.info("计算每个标签下问题的总数");
        List<Tag> tagList = tagService.findAll();
        tagList.forEach(tag -> setSaveTagQuestionCount(tag));

    }


    private void setSaveTagQuestionCount(Tag tag){
        long questioncount = questionService.countByTagIdAndDeleteFlg(tag.getId(),0);
        long articleCount = articleService.countByTagIdAndDeleteFlg(tag.getId(),0);
        tag.setQuestionCount(questioncount);
        tag.setQuestionCount(articleCount);
        tagService.save(tag);

    }
}
