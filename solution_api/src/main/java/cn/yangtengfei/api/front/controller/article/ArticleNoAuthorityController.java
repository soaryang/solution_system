package cn.yangtengfei.api.front.controller.article;


import cn.yangtengfei.api.config.PageResultModel;
import cn.yangtengfei.api.config.RestResult;
import cn.yangtengfei.api.exception.CommonException;
import cn.yangtengfei.api.front.view.article.ArticleView;
import cn.yangtengfei.model.course.Article;
import cn.yangtengfei.model.question.Tag;
import cn.yangtengfei.service.article.ArticleService;
import cn.yangtengfei.service.question.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping(value = "/v1/api/article")
public class ArticleNoAuthorityController {

    @Autowired
    private TagService tagService;

    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public RestResult findAll(HttpServletRequest request, Integer pageNumber , Integer pageSize) throws CommonException {

        RestResult restResult = new RestResult();
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
        restResult.setCode("200");
        restResult.setData(pageResultModel);
        return restResult;
    }

    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    public RestResult findById(@PathVariable("id") String id){
        RestResult restResult = new RestResult();
        Article article =  articleService.findById(id);
        String tagId = article.getTagId();
        Tag tag = tagService.findById(tagId);
        ArticleView articleView = new ArticleView();
        BeanUtils.copyProperties(article,articleView);
        articleView.setTagName(tag.getName());
        restResult.setCode("200");
        restResult.setData(articleView);
        return restResult;
    }
}
