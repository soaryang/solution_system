package cn.yangtengfei.api.service.authority;


import cn.yangtengfei.api.cacheService.authority.AuthorityCacheService;
import cn.yangtengfei.api.cacheService.user.UserCacheService;
import cn.yangtengfei.api.config.Result;
import cn.yangtengfei.api.exception.CommonException;
import cn.yangtengfei.api.service.user.ApiUserService;
import cn.yangtengfei.api.util.BCrypt;
import cn.yangtengfei.api.util.ErrorCode;
import cn.yangtengfei.api.util.UserTokenConst;
import cn.yangtengfei.api.util.cont.cacheConst.UserCacheConst;
import cn.yangtengfei.api.view.user.UserView;
import cn.yangtengfei.model.user.User;
import cn.yangtengfei.service.user.UserService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.connection.RedisServer;
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
    private ApiUserService apiUserService;

    @Autowired
    private AuthorityCacheService authorityCacheService;

    @Autowired
    private RedisServer redisServer;


    public User userLogin(String userName,String password,HttpServletResponse response) throws Exception {
        User user = userCacheService.findUserByName(userName);
        if(user==null){
            throw new CommonException(ErrorCode.Auth_Error_Code.USERNAME_IS_ERROR,"USERNAME IS ERROR");
        }
        UserView userView = apiUserService.finUserInfo(user.getId());

        Object object = redisServer.get(UserCacheConst.USER_PASSWORD_CACHE_KEY+userView.getOpenId());

        if(object!=null){
            String cachePassword = String.valueOf(object);
            if( !BCrypt.checkpw(password,cachePassword)){
                throw new CommonException(ErrorCode.Auth_Error_Code.PASSWORD_IS_ERROR,"PASSWORD IS ERROR");
            }
        }else{
            throw new CommonException(ErrorCode.Auth_Error_Code.PASSWORD_IS_NULL,"PASSWORD IS NULL");
        }


        /*if( !BCrypt.checkpw(password,user.getPassword())){
            throw new CommonException(ErrorCode.Auth_Error_Code.PASSWORD_IS_ERROR,"PASSWORD IS ERROR");
        }*/
        authorityCacheService.setUserInfoIntoCookie(response,user);
        return user;
    }


}
