package cn.yangtengfei.api.service.authority;


import cn.yangtengfei.api.cacheService.authority.AuthorityCacheService;
import cn.yangtengfei.api.cacheService.user.UserCacheService;
import cn.yangtengfei.api.config.Result;
import cn.yangtengfei.api.exception.CommonException;
import cn.yangtengfei.api.util.BCrypt;
import cn.yangtengfei.api.util.ErrorCode;
import cn.yangtengfei.api.util.UserTokenConst;
import cn.yangtengfei.model.user.User;
import cn.yangtengfei.service.user.UserService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Service
public class AuthorityService {

    @Autowired
    private UserCacheService userCacheService;

    @Autowired
    private AuthorityCacheService authorityCacheService;


    public User userLogin(String userName,String password,HttpServletResponse response) throws Exception {
        User user = userCacheService.findUserByName(userName);
        if(user==null){
            throw new CommonException(ErrorCode.Auth_Error_Code.USERNAME_IS_ERROR,"USERNAME IS ERROR");
        }
        if( !BCrypt.checkpw(password,user.getPassword())){
            throw new CommonException(ErrorCode.Auth_Error_Code.PASSWORD_IS_ERROR,"PASSWORD IS ERROR");
        }
        authorityCacheService.setUserInfoIntoCookie(response,user);
        return user;
    }


}
