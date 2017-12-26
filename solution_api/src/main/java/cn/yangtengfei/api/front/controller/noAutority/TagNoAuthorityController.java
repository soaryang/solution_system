package cn.yangtengfei.api.front.controller.noAutority;

import cn.yangtengfei.api.cacheService.question.SolutionCacheService;
import cn.yangtengfei.api.config.PageResultModel;
import cn.yangtengfei.api.config.Result;
import cn.yangtengfei.api.server.view.question.QuestionView;
import cn.yangtengfei.api.server.view.question.SolutionView;
import cn.yangtengfei.api.server.view.question.TagView;
import cn.yangtengfei.api.service.dataService.question.ApiQuestionService;
import cn.yangtengfei.api.service.dataService.question.ApiTagService;
import cn.yangtengfei.model.question.Question;
import cn.yangtengfei.model.question.Solution;
import cn.yangtengfei.model.question.Tag;
import cn.yangtengfei.util.ListUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/v1/api/tag")
public class TagNoAuthorityController {

    @Autowired
    private ApiTagService apiTagService;

    @Autowired
    private ApiQuestionService apiQuestionService;

    @Autowired
    private SolutionCacheService solutionCacheService;


    @RequestMapping(value = "/{tagId}", method = RequestMethod.GET)
    public Result findAllByTagId(@PathVariable("tagId") String tagId){
        TagView tagView =  apiTagService.findById(tagId);
        Result result = new Result();
        result.setCode("200");
        result.setData(tagView);
        return result;
    }

}
