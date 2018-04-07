package cn.yangtengfei.api.front.controller.user;


import cn.yangtengfei.api.cacheService.authority.AuthorityCacheService;
import cn.yangtengfei.api.cacheService.user.GitHubUserCacheService;
import cn.yangtengfei.api.config.Result;
import cn.yangtengfei.api.util.cont.UserTokenConst;
import cn.yangtengfei.model.user.GitHubUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping(value = "/v1/front/user")
public class NormalUserController {


    @Autowired
    private AuthorityCacheService authorityCacheService;

    @Autowired
    private GitHubUserCacheService gitHubUserCacheService;

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Result findById(HttpServletRequest request, HttpServletResponse response){

        Result result = new Result();
        Cookie[] cookies =request.getCookies();
        String key = "";
        if(cookies!=null && cookies.length!=0) {
            for (Cookie cookie : cookies) {
                if (UserTokenConst.COOKIE_KEY.equals(cookie.getName())) {
                    key = cookie.getValue();
                }
            }
        }
        /*if (!StringUtils.isEmpty(key)) {
            if (!authorityCacheService.chekAuthKeyIsExist(key)) {
            } else {
                //每次操作后增加session时间
                authorityCacheService.addSessionTime(key, response);
            }
        }*/
        result.setCode("200");
        GitHubUserInfo gitHubUserInfo =  gitHubUserCacheService.findByLogin(authorityCacheService.getAuthKey(key));
        result.setData(gitHubUserInfo);

        return result;
    }
}
