package cn.yangtengfei.api.security.login;

import cn.yangtengfei.api.util.TokenUtil;
import cn.yangtengfei.api.util.UserTokenConst;
import cn.yangtengfei.model.user.User;
import cn.yangtengfei.model.user.UserRole;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class RestAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
  
    @Override  
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String userName = request.getParameter("username");
        String id = "qweqweqweqweqweqwqweqqeweqwq";
        User user = new User();
        user.setName(userName);
        user.setId(id);
        List<UserRole> userRoleList = new ArrayList<>();
        UserRole userRole = new UserRole();
        userRole.setName("USER");
        userRoleList.add(userRole);

        /*String key = TokenUtil.createAuthKey(id);
        System.out.println("================"+key);
        Cookie authKeyCookie = new Cookie(UserTokenConst.COOKIE_AUTH_KEY, key);
        authKeyCookie.setMaxAge(3600*3);
        authKeyCookie.setPath("/");
        response.addCookie(authKeyCookie);*/


        response.setContentType("application/json");  
        response.setCharacterEncoding("UTF-8");  
        response.getWriter().write("{\"result\":\"ok\"}");  
        response.getWriter().flush();  
          
    }  
  
}  