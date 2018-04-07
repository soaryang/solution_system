package cn.yangtengfei.service.user;


import cn.yangtengfei.model.user.GitHubUserInfo;
import cn.yangtengfei.model.user.WechatUser;
import cn.yangtengfei.repository.user.GitHubUserInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class GitHubUserInfoService {

    @Autowired
    private GitHubUserInfoRepository gitHubUserInfoRepository;


    public GitHubUserInfo findById(String id){
        return gitHubUserInfoRepository.findOne(id);
    }

    public GitHubUserInfo findByGitHubId(String id){
        return gitHubUserInfoRepository.findByGitHubId(id);
    }

    public GitHubUserInfo findByLogin(String login){
        return gitHubUserInfoRepository.findAllByLogin(login);
    }

    public GitHubUserInfo save(GitHubUserInfo gitHubUserInfo){
        GitHubUserInfo gitHubUserInfoTemp = gitHubUserInfoRepository.findByGitHubId(gitHubUserInfo.getGitHubId());
        Date date = new Date();
        if(gitHubUserInfoTemp==null){
            gitHubUserInfo.setCreateTime(date);
        }
        gitHubUserInfo.setUpdateTime(date);

        return gitHubUserInfoRepository.save(gitHubUserInfo);
    }
}
