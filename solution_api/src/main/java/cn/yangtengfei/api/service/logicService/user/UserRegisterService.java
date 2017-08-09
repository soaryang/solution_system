package cn.yangtengfei.api.service.logicService.user;


import cn.yangtengfei.api.cacheService.user.UserCacheService;
import cn.yangtengfei.api.service.logicService.common.AjaxResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class UserRegisterService {

    @Autowired
    private UserCacheService userCacheService;

    @Autowired
    private AjaxResponseService ajaxResponseService;

    public boolean checkRegisterUserMailSend(HttpServletRequest request, HttpServletResponse response){
        return  false;
    }
}
