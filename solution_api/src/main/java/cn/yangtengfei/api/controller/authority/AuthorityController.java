package cn.yangtengfei.api.controller.authority;


import cn.yangtengfei.api.config.PageResultModel;
import cn.yangtengfei.api.config.Result;
import cn.yangtengfei.api.controller.base.BaseController;
import cn.yangtengfei.api.controller.question.SolutionController;
import cn.yangtengfei.api.exception.CommonException;
import cn.yangtengfei.api.service.authority.AuthorityService;
import cn.yangtengfei.api.util.ErrorCode;
import cn.yangtengfei.model.question.Question;
import cn.yangtengfei.model.user.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/v1/api")
public class AuthorityController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(AuthorityController.class);

    @Autowired
    private AuthorityService authorityService;
    
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
