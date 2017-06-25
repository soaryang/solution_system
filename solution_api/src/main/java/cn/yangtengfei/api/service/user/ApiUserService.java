package cn.yangtengfei.api.service.user;

import cn.yangtengfei.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/5/28 0028.
 */

@Service
public class ApiUserService {

    @Autowired
    private UserService userService;
}
