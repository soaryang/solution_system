package cn.yangtengfei.api.front.controller.noAutority;

import cn.yangtengfei.api.config.PageResultModel;
import cn.yangtengfei.api.config.RestResult;
import cn.yangtengfei.api.server.view.question.TagView;
import cn.yangtengfei.api.service.dataService.question.ApiTagService;
import cn.yangtengfei.model.question.Tag;
import cn.yangtengfei.service.question.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/v1/api/tag")
public class TagNoAuthorityController {

    @Autowired
    private ApiTagService apiTagService;

    @Autowired
    private TagService tagService;

    @RequestMapping(value = "/page/{pageNo}/{size}", method = RequestMethod.GET)
    public PageResultModel findAll(@PathVariable("pageNo") Integer pageNo, @PathVariable("size") Integer size){
        int useStatus = 1;
        int start = pageNo-1;
        PageResultModel pageResultModel = new PageResultModel();
        Page<Tag> questionTypes = null;
        questionTypes = tagService.findByUseStatusOrderByQuestionCountDesc(useStatus,start,size);
        pageResultModel.setTotal(questionTypes.getTotalElements());
        pageResultModel.setRows(questionTypes.getContent());
        return  pageResultModel;
    }

    @RequestMapping(value = "/{tagId}", method = RequestMethod.GET)
    public RestResult findAllByTagId(@PathVariable("tagId") String tagId){
        TagView tagView =  apiTagService.findById(tagId);
        RestResult restResult = new RestResult();
        restResult.setCode("200");
        restResult.setData(tagView);
        return restResult;
    }

}
