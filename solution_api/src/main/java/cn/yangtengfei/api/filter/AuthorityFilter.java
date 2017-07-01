package cn.yangtengfei.api.filter;

import cn.yangtengfei.api.exception.CommonException;
import cn.yangtengfei.api.util.ErrorCode;
import cn.yangtengfei.api.util.UserTokenConst;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthorityFilter  implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if( handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            //Method method = handlerMethod.getMethod();
            String url = request.getRequestURI();;
            if( url.indexOf("/v1/api/admin") != -1){
                Cookie[] cookies =request.getCookies();
                if(cookies!=null && cookies.length==0){
                    String cookieValue = "";
                    for (Cookie cookie : cookies) {
                        if (UserTokenConst.COOKIE_USER_KEY.equals(cookie.getName())) {
                            cookieValue = cookie.getValue();
                        }
                    }
                    if (!StringUtils.isEmpty(cookieValue)) {

                    }else {
                        //throw new CommonException(ErrorCode.Auth_Error_Code.NO_ACCESS,"NO_ACCESS");
                        response.setStatus(401);
                    }
                }else{
                    //throw new CommonException(ErrorCode.Auth_Error_Code.NO_ACCESS,"NO_ACCESS");
                    response.setStatus(401);
                }
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
