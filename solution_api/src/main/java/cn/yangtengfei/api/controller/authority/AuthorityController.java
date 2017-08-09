package cn.yangtengfei.api.controller.authority;


import cn.yangtengfei.api.config.Result;
import cn.yangtengfei.api.controller.base.BaseController;
import cn.yangtengfei.api.exception.CommonException;
import cn.yangtengfei.api.service.dataService.user.ApiUserService;
import cn.yangtengfei.api.service.logicService.authority.AuthorityService;
import cn.yangtengfei.api.util.ErrorCode;
import cn.yangtengfei.api.view.user.UserView;
import cn.yangtengfei.model.user.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/v1/api")
public class AuthorityController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(AuthorityController.class);

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private ApiUserService apiUserService;

    @RequestMapping(value = "/captcha/{code}", method = {RequestMethod.GET, RequestMethod.POST})
    public Result captcha(@PathVariable("code") String code) throws Exception {
        return null;
    }
    @RequestMapping(value = "/register", method = {RequestMethod.GET, RequestMethod.POST})
    public Result register(@ModelAttribute UserView userView) throws Exception {
        Result result = new Result();
        apiUserService.saveUserByUserView(userView);
        result.setCode("200");
        return result;
    }

    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public Result findAll(HttpServletRequest request,HttpServletResponse response) throws Exception {
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            throw new CommonException(ErrorCode.Auth_Error_Code.USERNAME_OR_PASSWORD_IS_NULL, "USERNAME OR  PASSWORD IS NULL");
        }
        Result result = new Result();
        User user = authorityService.userLogin(userName, password,response);
        if(user!=null){
            result.setCode("200");
        }else{
            result.setCode("500");
        }
        return result;
    }
}
