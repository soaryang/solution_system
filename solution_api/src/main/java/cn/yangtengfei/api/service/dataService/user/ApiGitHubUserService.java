package cn.yangtengfei.api.service.dataService.user;

import cn.yangtengfei.api.front.view.user.GitHubUserView;
import cn.yangtengfei.model.user.GitHubUserInfo;
import cn.yangtengfei.service.user.GitHubUserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ApiGitHubUserService {

    @Autowired
    private GitHubUserInfoService gitHubUserInfoService;

    public GitHubUserInfo findById(String id){
        return gitHubUserInfoService.findById(id);
    }

    public GitHubUserInfo findByGitHubId(String id){
        return gitHubUserInfoService.findByGitHubId(id);
    }

    public GitHubUserInfo save(GitHubUserView gitHubUserView){
        GitHubUserInfo gitHubUserInfo = new GitHubUserInfo();

        BeanUtils.copyProperties(gitHubUserView,gitHubUserInfo);
        return gitHubUserInfoService.save(gitHubUserInfo);
    }
}
