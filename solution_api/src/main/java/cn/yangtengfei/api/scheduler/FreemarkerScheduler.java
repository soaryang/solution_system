package cn.yangtengfei.api.scheduler;


import cn.yangtengfei.api.service.staticService.FreemarkerService;
import cn.yangtengfei.model.course.Article;
import cn.yangtengfei.model.question.Tag;
import cn.yangtengfei.service.article.ArticleService;
import cn.yangtengfei.service.question.TagService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@Configuration
@EnableScheduling
public class FreemarkerScheduler {

    @Resource
    Map<String, FreemarkerService> freemarkerServiceMap;

    @Autowired
    private TagService tagService;

    @Autowired
    private ArticleService articleService;


    @Value("${scheduler.tagList}")
    private String tagArrayList;


    @Scheduled(cron = "0/30 * * * * ?")
    public void createArticleFile() {
        List<Article> articleList = articleService.findAll();
        FreemarkerService freemarkerArticleService = freemarkerServiceMap.get("articleDetailFreemarkService");
        articleList.stream().forEach(article -> {
            Map<String, Object> dataMap = Maps.newHashMap();
            dataMap.put("id", article.getId());
            dataMap.put("content", article.getContent());
            freemarkerArticleService.createHtml(dataMap);
        });

    }

    @Scheduled(cron = "0/30 * * * * ?")
    public void createIndexPage() {
        log.info("createIndexPagecreateIndexPagecreateIndexPagecreateIndexPage");
        FreemarkerService freemarkerService = freemarkerServiceMap.get("indexPageFreemarkerService");


        Map<String, Object> map = Maps.newHashMap();
        if (StringUtils.isEmpty(tagArrayList)) {
            return;
        }

        //String[] tagArray = tagArrayList.split(",");
        Page<Tag> page = tagService.findByUseStatus(1, 1, 100);

        List<Tag> tagList = page.getContent();
        log.info("tagList:{}",tagList);
        /*String[] tagArray = {
                "5b8e1c1c7d50eb103d27fd7c"//设计模式
                , "591e5c4654815a0ea114fe43"//spring
                , "597cb09e7d50eb689bad1030"//springboot
                , "5f141da57d50eb04b9ab0a84"//springcloud
                , "591e5c8f54815a0ea114ffee"//redis
                , "591e5c4454815a0ea114fe27"//mysql
                , "5f141eca7d50eb04b9ab0a85"//架构设计
        };*/

        //List<Tag> tagList = tagService.findByIdIn(Arrays.asList(tagArray));
        List<Object> list = Lists.newArrayList();
        tagList.stream().forEach(e -> {
            Map<String, Object> tempMap = Maps.newHashMap();
            tempMap.put("tagId", e.getId());
            tempMap.put("tagName", e.getName());
            Page<Article> articlesPage = articleService.findByTagIdAndDeleteFlgOrderByUpdateTimeDesc(e.getId(), 0, 0, 5);
            if (!CollectionUtils.isEmpty(articlesPage.getContent())) {
                tempMap.put("articleList", articlesPage.getContent());
                list.add(tempMap);
            }
        });
        map.put("modelList", list);
        freemarkerService.createHtml(map);
    }

}
