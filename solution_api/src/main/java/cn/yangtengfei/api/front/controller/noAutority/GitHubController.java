package cn.yangtengfei.api.front.controller.noAutority;

import cn.yangtengfei.api.cacheService.authority.AuthorityCacheService;
import cn.yangtengfei.api.config.Result;
import cn.yangtengfei.api.exception.CommonException;
import cn.yangtengfei.api.front.controller.user.GitHubUserView;
import cn.yangtengfei.api.server.view.user.UserView;
import cn.yangtengfei.api.service.dataService.user.ApiGitHubUserService;
import cn.yangtengfei.model.user.GitHubUserInfo;
import cn.yangtengfei.model.user.User;
import cn.yangtengfei.service.user.UserService;
import cn.yangtengfei.util.DateUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Date;


@Slf4j
@RestController
@RequestMapping(value = "/v1")
public class GitHubController {

    /*@RequestMapping(value="/github/vaild",method = RequestMethod.GET)
    public String  RegisteredByGithub(String code){
        log.info("================code:{}",code);

        return code;
    }

    @RequestMapping(value="/node/Http",method = RequestMethod.POST)
    public String   nodeHttpPost(GitHubUserView gitHubUserView){
        log.info("gitHubUserView:{}",JSON.toJSONString(gitHubUserView));
        return JSON.toJSONString(gitHubUserView);
    }*/

    @Autowired
    private AuthorityCacheService authorityCacheService;

    @Autowired
    private UserService userService;

    @Autowired
    private ApiGitHubUserService apiGitHubUserService;

    @RequestMapping(value="/api/github/userAdd",method = RequestMethod.POST)
    public Result RegisteredByGithub(HttpServletRequest request, HttpServletResponse response) throws CommonException {
        String login=request.getParameter("login");log.info("========="+login);
        String id=request.getParameter("id");log.info("========="+id);
        String avatar_url=request.getParameter("avatar_url");log.info("========="+avatar_url);
        String gravatar_id=request.getParameter("gravatar_id");log.info("========="+gravatar_id);
        String url=request.getParameter("url");log.info("========="+url);
        String html_url=request.getParameter("html_url");log.info("========="+html_url);
        String followers_url=request.getParameter("followers_url");log.info("========="+followers_url);
        String following_url=request.getParameter("following_url");log.info("========="+following_url);
        String gists_url=request.getParameter("gists_url");log.info("========="+gists_url);
        String starred_url=request.getParameter("starred_url");log.info("========="+starred_url);
        String subscriptions_url=request.getParameter("subscriptions_url");log.info("========="+subscriptions_url);
        String organizations_url=request.getParameter("organizations_url");log.info("========="+organizations_url);
        String repos_url=request.getParameter("repos_url");log.info("========="+repos_url);
        String events_url=request.getParameter("events_url");log.info("========="+events_url);
        String received_events_url=request.getParameter("received_events_url");log.info("========="+received_events_url);
        String type=request.getParameter("type");log.info("========="+type);
        String site_admin=request.getParameter("site_admin");log.info("========="+site_admin);
        String name=request.getParameter("name");log.info("========="+name);
        String company=request.getParameter("company");log.info("========="+company);
        String blog=request.getParameter("blog");log.info("========="+blog);
        String location=request.getParameter("location");log.info("========="+location);
        String email=request.getParameter("email");log.info("========="+email);
        String hireable=request.getParameter("hireable");log.info("========="+hireable);
        String bio=request.getParameter("bio");log.info("========="+bio);
        String public_repos=request.getParameter("public_repos");log.info("========="+public_repos);
        String public_gists=request.getParameter("public_gists");log.info("========="+public_gists);
        String followers=request.getParameter("followers");log.info("========="+followers);
        String following=request.getParameter("following");log.info("========="+following);
        String created_at=request.getParameter("created_at");log.info("========="+created_at);
        String updated_at=request.getParameter("updated_at");log.info("========="+updated_at);
        String private_gists=request.getParameter("private_gists");log.info("========="+private_gists);
        String total_private_repos=request.getParameter("total_private_repos");log.info("========="+total_private_repos);
        String owned_private_repos=request.getParameter("owned_private_repos");log.info("========="+owned_private_repos);
        String disk_usage=request.getParameter("disk_usage");log.info("========="+disk_usage);
        String collaborators=request.getParameter("collaborators");log.info("========="+collaborators);
        String two_factor_authentication=request.getParameter("two_factor_authentication");log.info("========="+two_factor_authentication);


        if(StringUtils.isBlank(id) || StringUtils.isBlank(avatar_url) || StringUtils.isBlank(login)){
            throw new CommonException("401","用户信息不完整");
        }

        GitHubUserInfo gitHubUserInfo = apiGitHubUserService.findById(id);
        GitHubUserView gitHubUserView = new GitHubUserView();
        gitHubUserView.setId(id);
        gitHubUserView.setAvatar_url(avatar_url);
        gitHubUserView.setLogin(login);
        Date currentDate =  DateUtils.getCurrentDate();
        if(gitHubUserInfo!=null){
            User user = new User();
            user.setCreateTime(currentDate);
            user.setUpdateTime(currentDate);
            user = userService.save(user);
            gitHubUserView.setUserId(user.getId());
            gitHubUserInfo = apiGitHubUserService.save(gitHubUserView);
        }else{
            gitHubUserInfo = apiGitHubUserService.save(gitHubUserView);
        }

        Result result = new Result();
        result.setCode("200");
        result.setData(gitHubUserInfo);
        return result;



        /*try {
            authorityCacheService.setUserInfoIntoCookie(response,gitHubUserView.getUserId(),gitHubUserView.getLogin(),gitHubUserView.getAvatar_url());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/


        //log.info("avatar_url:{}",avatar_url);
        //log.info("userInfo:{}",JSON.toJSONString(gitHubUserView));
        //GitHubUserView gitHubUserView = JSON.parseObject(userInfo,GitHubUserView.class);
        //log.info(JSON.toJSONString(gitHubUserView));
    }

}
