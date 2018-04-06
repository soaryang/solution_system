package cn.yangtengfei.api.front.controller.index;

import cn.yangtengfei.api.config.Result;
import cn.yangtengfei.api.service.dataService.question.ApiTagService;
import cn.yangtengfei.model.question.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/v1/index")
public class IndexController {
    @Autowired
    private ApiTagService apiTagService;

    @RequestMapping(value = "/findTags", method = RequestMethod.GET)
    public Result findTags(){
        Result result = new Result();
        Page<Tag> tagPage = apiTagService.findByUseStatus(0,1,20);
        result.setCode("200");
        result.setMessage("OK");
        result.setData(tagPage.getContent());
        return result;
    }

}
