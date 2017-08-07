package cn.yangtengfei.api.filter;

import cn.yangtengfei.api.cacheService.authority.AuthorityCacheService;
import cn.yangtengfei.api.controller.base.BaseController;
import cn.yangtengfei.api.logicService.user.UserLogicService;
import cn.yangtengfei.api.util.cont.UserTokenConst;
import cn.yangtengfei.model.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Service
@Slf4j
public class AuthorityFilter  implements HandlerInterceptor {

    @Autowired
    private AuthorityCacheService authorityCacheService;

    @Autowired
    private UserLogicService userLogicService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if( handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            String cookieValue = "";
            String url = request.getRequestURI();
            String ip = request.getRemoteAddr();
            if(url.indexOf("/v1/api/register")!=-1){
                log.info("用户注册");
                //1.判断用户名是否存在
            }else if(url.indexOf("/v1/api/mail/sendMail")!=-1){
                log.info("IP : " + ip);

                return userLogicService.checkRegisterUserMailSend(request,response);
            }else if( url.indexOf("/v1/api/admin") != -1){
                Cookie[] cookies =request.getCookies();
                if(cookies!=null && cookies.length!=0){

                    for (Cookie cookie : cookies) {
                        if (UserTokenConst.COOKIE_USER_KEY.equals(cookie.getName())) {
                            cookieValue = cookie.getValue();
                        }
                    }
                    if (!StringUtils.isEmpty(cookieValue)) {
                        if (!authorityCacheService.chekAuthKeyIsExist(cookieValue)) {
                            response.setStatus(401);
                            return false;
                        } else {
                            //每次操作后增加session时间
                            authorityCacheService.addSessionTime(cookieValue, response);
                        }
                    }else {
                        //throw new CommonException(ErrorCode.Auth_Error_Code.NO_ACCESS,"NO_ACCESS");
                        response.setStatus(401);
                        return false;
                    }
                }else{
                    //throw new CommonException(ErrorCode.Auth_Error_Code.NO_ACCESS,"NO_ACCESS");
                    response.setStatus(401);
                    return false;
                }
            }
            User  user = authorityCacheService.getUer(cookieValue);
            if( handlerMethod.getBean() instanceof BaseController){
                BaseController baseAdminController = (BaseController) handlerMethod.getBean();
                baseAdminController.setUser(user);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
