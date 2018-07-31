package cn.yangtengfei.api.front.controller.index;

import cn.yangtengfei.api.config.RestResult;
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
    public RestResult findTags(){
        RestResult restResult = new RestResult();
        Page<Tag> tagPage = apiTagService.findByUseStatus(0,1,20);
        restResult.setCode("200");
        restResult.setMessage("OK");
        restResult.setData(tagPage.getContent());
        return restResult;
    }

}
