package cn.yangtengfei.api.server.controller.article;

import cn.yangtengfei.api.config.PageResultModel;
import cn.yangtengfei.api.config.RestResult;
import cn.yangtengfei.api.exception.CommonException;
import cn.yangtengfei.api.server.controller.base.BaseController;
import cn.yangtengfei.api.service.staticService.FreemarkerService;
import cn.yangtengfei.model.course.Article;
import cn.yangtengfei.model.question.Tag;
import cn.yangtengfei.service.article.ArticleService;
import cn.yangtengfei.service.question.TagService;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/v1/api/admin/article")
public class ArticleController extends BaseController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private TagService tagService;

    @Resource
    Map<String, FreemarkerService> freemarkerServiceMap;

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public PageResultModel findAll(HttpServletRequest request, Integer pageNumber , Integer pageSize) throws CommonException {
        String tagId = request.getParameter("tagId");

        Tag tag = tagService.findById(tagId);
        if(tag==null){
            throw new CommonException("error","标签不存在");
        }
        int start = pageNumber-1;
        PageResultModel pageResultModel = new PageResultModel();
        Page<Article> articlesPage = articleService.findByTagIdAndDeleteFlgOrderByUpdateTimeDesc(tagId,0,start,pageSize);
        pageResultModel.setTotal(articlesPage.getTotalElements());
        pageResultModel.setRows(articlesPage.getContent());
        pageResultModel.setOtherData(tag);
        return  pageResultModel;
    }

    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
    public RestResult findById(@PathVariable("id") String id){
        RestResult restResult = new RestResult();
        Article Article =  articleService.findById(id);
        restResult.setCode("200");
        restResult.setData(Article);
        return restResult;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public RestResult findAll(HttpServletRequest request) throws CommonException {

        FreemarkerService freemarkerService = freemarkerServiceMap.get("articleDetailFreemarkService");
        String id = request.getParameter("id");
        String tagId = request.getParameter("tagId");
        String articleName = request.getParameter("articleName");
        String content = request.getParameter("content");

        Article article = new Article();
        if(!StringUtils.isBlank(id)){
            article.setId(id);
            article = articleService.findById(id);
            if(article==null){
                throw new CommonException("error","文章不存在");
            }
        }else{
            article.setTagId(tagId);
        }
        article.setArticleName(articleName);
        article.setContent(content);


        article = articleService.save(article);
        System.out.println(article);


        Map<String, Object> dataMap = Maps.newHashMap();

        dataMap.put("id",article.getId());
        dataMap.put("content",article.getContent());
        freemarkerService.createHtml(dataMap);
        RestResult restResult = new RestResult();
        restResult.setCode("200");
        return restResult;
    }

    @RequestMapping(value = "/createFree", method = RequestMethod.GET)
    public RestResult createFree(HttpServletRequest request) throws CommonException {

        FreemarkerService freemarkerService = freemarkerServiceMap.get("articleDetailFreemarkService");
        Article article = articleService.findById("5b6fe7ac7d50eb103d27fd6a");
        Map<String, Object> dataMap = Maps.newHashMap();

        dataMap.put("id",article.getId());
        dataMap.put("content",article.getContent());
        freemarkerService.createHtml(dataMap);
        RestResult restResult = new RestResult();
        return restResult;
    }


    @RequestMapping(value = "/del/{id}", method = RequestMethod.GET)
    public RestResult del(@PathVariable("id") String id){
        articleService.delete(id);
        RestResult restResult = new RestResult();
        restResult.setCode("200");
        return restResult;
    }

}
