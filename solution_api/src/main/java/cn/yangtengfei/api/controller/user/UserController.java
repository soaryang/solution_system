package cn.yangtengfei.api.controller.user;

import cn.yangtengfei.api.config.PageResultModel;
import cn.yangtengfei.api.config.Result;
import cn.yangtengfei.api.controller.base.BaseController;
import cn.yangtengfei.api.controller.wechat.WeChatController;
import cn.yangtengfei.api.exception.CommonException;
import cn.yangtengfei.api.service.user.ApiUserService;
import cn.yangtengfei.api.util.BCrypt;
import cn.yangtengfei.api.view.user.UserView;
import cn.yangtengfei.model.user.User;
import cn.yangtengfei.service.user.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/5/28 0028.
 */

@RestController
@RequestMapping(value = "/v1/api/admin/user")
public class UserController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private ApiUserService apiUserService;

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public PageResultModel findAll(Integer subscribeState,Integer pageNumber , Integer pageSize){

        logger.info("subscribeState:{},pageNumber:{},pageSize:{}",subscribeState,pageNumber,pageSize);
        return apiUserService.findPage(0,subscribeState,pageNumber-1,pageSize);
    }

    @RequestMapping(value = "/findUserInfo", method = RequestMethod.GET)
    public Result findUserInfo(String id){

        Result result = new Result();
        result.setCode("200");
        result.setData(apiUserService.finUserInfo(id));
        return result;
    }


    @RequestMapping(value = "/save", method = RequestMethod.GET)
    public Result save(@ModelAttribute UserView userView){

        Result result = new Result();
        apiUserService.saveUser(userView);
        result.setCode("200");
        return result;
        //logger.info("subscribeState:{},pageNumber:{},pageSize:{}",subscribeState,pageNumber,pageSize);
        //return apiUserService.findPage(0,subscribeState,pageNumber-1,pageSize);
    }


    /*@RequestMapping(value = "/save", method = {RequestMethod.GET,RequestMethod.POST})
    private Result saveUser(){
        User user = new User();
        String password = BCrypt.hashpw("***20121226NOTFORGET",BCrypt.gensalt());
        user.setName("admin");
        user.setPassword(password);
        userService.save(user);
        Result result = new Result();
        result.setCode("200");
        return result;
    }*/
}