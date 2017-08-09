package cn.yangtengfei.api.service.logicService.user;

import cn.yangtengfei.api.cacheService.user.RegisterCacheService;
import cn.yangtengfei.api.cacheService.user.UserCacheService;
import cn.yangtengfei.api.config.Result;
import cn.yangtengfei.api.exception.CommonException;
import cn.yangtengfei.api.service.logicService.common.AjaxResponseService;
import cn.yangtengfei.model.user.User;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
@Slf4j
public class UserLogicService {

    @Autowired
    private UserCacheService userCacheService;

    @Autowired
    private RegisterCacheService registerCacheService;

    @Autowired
    private AjaxResponseService ajaxResponseService;

    public boolean checkRegisterCodeIsExist(HttpServletRequest request,HttpServletResponse response) throws CommonException {
        String verifyCode = request.getParameter("verifyCode");
        String ip = request.getRemoteAddr();
        String code = registerCacheService.findMailRegisterCode(ip);
        Result result = new Result();
        if(StringUtils.isEmpty(code)){
            //result.setCode("500");
            //result.setMessage("注册码失效");
            //ajaxResponseService.returnJson(response, JSON.toJSONString(result));
            throw new CommonException("500","注册码失效");
        }else if(!verifyCode.equals(code)){
            throw new CommonException("500","注册码错误");
        }
        return true;
    }

    public boolean checkRegisterUserMailSend(HttpServletRequest request,HttpServletResponse response) throws CommonException {

        String ip = request.getRemoteAddr();
        String email = request.getParameter("email");
        //判断当前ip发送邮件的次数，如果超过一天超过3次，不让次ip的用户发送邮件了
        if(StringUtils.isEmpty(email)){
            throw new CommonException("500","邮件不存在");
        }
        //判断此邮箱是否注册过
        User user = userCacheService.findUserByEmail(email);
        if(user!=null){
            log.info("邮件已经注册过");
            throw new CommonException("500","邮件已经注册过");
        }

        //判断发送的次数
        int count = registerCacheService.findIpMailCount(ip);
        if(count>3){
            log.info("当前ip:{}发送的邮件次数已经超过上线，请24小时以后重新发送",ip);
            throw new CommonException("500","当前ip发送的邮件次数已经超过上线，请24小时以后重新发送");
        }else{
            registerCacheService.setIpMailCount(ip,count+1);
        }
        return true;
    }
}
