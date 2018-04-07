package cn.yangtengfei.api.cacheService.user;


import cn.yangtengfei.cacheKey.user.GitHubUserCacheKey;
import cn.yangtengfei.cacheKey.user.UserCacheKey;
import cn.yangtengfei.model.user.GitHubUserInfo;
import cn.yangtengfei.model.user.User;
import cn.yangtengfei.service.user.GitHubUserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GitHubUserCacheService {


    @Autowired
    private GitHubUserInfoService gitHubUserInfoService;

    @Cacheable(value=GitHubUserCacheKey.USER_KEY, key = "'"+GitHubUserCacheKey.USER_INFO_NAME_KEY+"'+#name")
    public GitHubUserInfo findByLogin(String login){
        return  gitHubUserInfoService.findByLogin(login);
    }

}
