package cn.yangtengfei.api.server.controller.user;

import cn.yangtengfei.api.config.PageResultModel;
import cn.yangtengfei.api.config.RestResult;
import cn.yangtengfei.api.server.controller.base.BaseController;
import cn.yangtengfei.api.service.dataService.user.ApiUserService;
import cn.yangtengfei.api.server.view.user.UserView;
import cn.yangtengfei.service.user.UserService;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public PageResultModel findAll(Integer subscribeState,Integer pageNumber , Integer pageSize){

        logger.info("subscribeState:{},pageNumber:{},pageSize:{}",subscribeState,pageNumber,pageSize);
        return apiUserService.findPage(0,subscribeState,pageNumber-1,pageSize);
    }

    @RequestMapping(value = "/findUserInfo", method = RequestMethod.GET)
    public RestResult findUserInfo(String id){

        RestResult restResult = new RestResult();
        restResult.setCode("200");
        restResult.setData(apiUserService.finUserInfo(id));
        return restResult;
    }


    @RequestMapping(value = "/save", method = {RequestMethod.GET,RequestMethod.POST})
    public RestResult save(@ModelAttribute UserView userView){

        RestResult restResult = new RestResult();
        apiUserService.saveUserByWechat(userView);
        restResult.setCode("200");
        logger.info("all:{}", JSON.toJSONString(userService.findAll()));
        return restResult;


        //logger.info("subscribeState:{},pageNumber:{},pageSize:{}",subscribeState,pageNumber,pageSize);
        //return apiUserService.findPage(0,subscribeState,pageNumber-1,pageSize);
    }


    /*@RequestMapping(value = "/save", method = {RequestMethod.GET,RequestMethod.POST})
    private RestResult saveUser(){
        User user = new User();
        String password = BCrypt.hashpw("***20121226NOTFORGET",BCrypt.gensalt());
        user.setName("admin");
        user.setPassword(password);
        userService.save(user);
        RestResult result = new RestResult();
        result.setCode("200");
        return result;
    }*/
}