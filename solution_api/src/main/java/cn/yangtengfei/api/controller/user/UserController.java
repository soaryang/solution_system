package cn.yangtengfei.api.controller.user;

import cn.yangtengfei.api.config.Result;
import cn.yangtengfei.api.controller.base.BaseController;
import cn.yangtengfei.api.util.BCrypt;
import cn.yangtengfei.model.user.User;
import cn.yangtengfei.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/5/28 0028.
 */

@RestController
@RequestMapping(value = "/v1/api/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/save", method = {RequestMethod.GET,RequestMethod.POST})
    private Result saveUser(){
        User user = new User();
        String password = BCrypt.hashpw("***20121226NOTFORGET",BCrypt.gensalt());
        user.setName("admin");
        user.setPassword(password);
        userService.save(user);
        Result result = new Result();
        result.setCode("200");
        return result;
    }
}
