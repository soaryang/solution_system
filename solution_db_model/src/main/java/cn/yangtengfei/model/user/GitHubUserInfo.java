package cn.yangtengfei.model.user;


import cn.yangtengfei.baseModel.BaseModel;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class GitHubUserInfo  extends BaseModel {

    private String id;
    private String login;

    private String userId;
    private String gitHubId;;
    private String avatar_url;
    private String gravatar_id;
    private String url;;
    private String html_url;
    private String followers_url;
    private String following_url;
    private String gists_url;
    private String starred_url;
    private String subscriptions_url;
    private String organizations_url;
    private String repos_url;
    private String events_url;
    private String received_events_url;
    private String type;;
    private String site_admin;
    private String name;
    private String company;
    private String blog;;
    private String location;
    private String email;
    private String hireable;
    private String bio;;
    private int public_repos;
    private int public_gists;
    private int followers;
    private int following;
    private Date created_at;
    private Date updated_at;
    private int private_gists;
    private int total_private_repos;
    private int owned_private_repos;
    private int disk_usage;
    private int collaborators;
    private boolean two_factor_authentication;
    private List<UserPlan> plan;
}
@Data class UserPlan{
    private String name;
    private String space;
    private int collaborators;
    private int private_repos;
}
