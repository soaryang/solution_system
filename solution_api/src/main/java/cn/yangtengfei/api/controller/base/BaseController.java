package cn.yangtengfei.api.controller.base;

import cn.yangtengfei.model.user.User;

/**
 * Created by Administrator on 2017/5/28 0028.
 */
public class BaseController {

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
