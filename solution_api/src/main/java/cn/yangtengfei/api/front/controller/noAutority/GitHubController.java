package cn.yangtengfei.api.front.controller.noAutority;

import cn.yangtengfei.api.front.controller.user.GitHubUserView;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@RestController
@RequestMapping(value = "/v1")
public class GitHubController {

    @RequestMapping(value="/github/vaild",method = RequestMethod.GET)
    public String  RegisteredByGithub(String code){
        log.info("================code:{}",code);

        return code;
    }

    @RequestMapping(value="/node/Http",method = RequestMethod.POST)
    public String   nodeHttpPost(GitHubUserView gitHubUserView){
        log.info("gitHubUserView:{}",JSON.toJSONString(gitHubUserView));
        return JSON.toJSONString(gitHubUserView);
    }

}
