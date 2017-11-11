package cn.yangtengfei.api.front.controller.noAutority;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping(value = "/v1")
public class GitHubController {

    @RequestMapping(value="/github/vaild",method = RequestMethod.GET)
    public String  RegisteredByGithub(String code){
        log.info("================code:{}",code);
        return code;
    }

}
