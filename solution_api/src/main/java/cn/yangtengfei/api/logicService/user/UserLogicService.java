package cn.yangtengfei.api.logicService.user;

import cn.yangtengfei.api.cacheService.user.RegisterCacheService;
import cn.yangtengfei.api.cacheService.user.UserCacheService;
import cn.yangtengfei.api.config.Result;
import cn.yangtengfei.api.logicService.common.AjaxResponseService;
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

    public boolean checkRegisterUserMailSend(HttpServletRequest request,HttpServletResponse response){

        Result result = new Result();
        String ip = request.getRemoteAddr();
        String email = request.getParameter("email");
        //判断当前ip发送邮件的次数，如果超过一天超过3次，不让次ip的用户发送邮件了
        if(StringUtils.isEmpty(email)){
            log.info("邮件不存在");
            result.setCode("500");
            ajaxResponseService.returnJson(response, JSON.toJSONString(request));
            return false;
        }
        //判断此邮箱是否注册过
        User user = userCacheService.findUserByEmail(email);
        if(user!=null){
            log.info("邮件已经注册过");
            result.setCode("500");
            result.setMessage("邮件已经注册过");
            ajaxResponseService.returnJson(response, JSON.toJSONString(request));
            return false;
        }
        //判断发送的次数
        int count = registerCacheService.findIpMailCount(ip);
        if(count>3){
            log.info("发送邮件的次数超过3次");
            result.setCode("500");
            result.setMessage("发送邮件的次数超过3次");
            ajaxResponseService.returnJson(response, JSON.toJSONString(result));
            return false;
        }else{
            registerCacheService.setIpMailCount(ip,count+1);
        }
        return true;
    }
}
