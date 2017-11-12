package cn.yangtengfei.api.front.controller.user;

import lombok.Data;

import java.util.List;


@Data
public class GitHubUserView {
    private String login;
    private String id;;
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
    private String public_repos;
    private String public_gists;
    private String followers;
    private String following;
    private String created_at;
    private String updated_at;
    private String private_gists;
    private String total_private_repos;
    private String owned_private_repos;
    private String disk_usage;
    private String collaborators;
    private String two_factor_authentication;
    private List<UserPlan> plan;
}

@Data
class UserPlan{
    private String name;
    private String space;
    private String collaborators;
    private String private_repos;
}
