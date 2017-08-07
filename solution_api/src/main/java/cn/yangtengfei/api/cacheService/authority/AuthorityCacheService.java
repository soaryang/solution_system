package cn.yangtengfei.api.cacheService.authority;


import cn.yangtengfei.api.cacheService.user.UserCacheService;
import cn.yangtengfei.api.util.BCrypt;
import cn.yangtengfei.api.util.cont.UserTokenConst;
import cn.yangtengfei.api.util.cont.cacheConst.AuthorityCacheConst;
import cn.yangtengfei.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

@Service
public class AuthorityCacheService {


    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserCacheService userCacheService;



    public String createAuthKey(String id,long time){
        String key = BCrypt.hashpw(id,BCrypt.gensalt());
        String authKey = AuthorityCacheConst.AUTHORITY_KEY_PREFIX+key;
        redisTemplate.opsForValue().set(authKey,id);
        redisTemplate.expire(authKey,time, TimeUnit.SECONDS);
        return key;
    }

    public boolean chekAuthKeyIsExist(String key){
        String authKey = "authKey:"+key;
        return redisTemplate.hasKey(authKey);
    }

    public User getUer(String key){
        String authKey = AuthorityCacheConst.AUTHORITY_KEY_PREFIX+key;
        Object object = redisTemplate.opsForValue().get(authKey);
        if(object!=null){
           return userCacheService.findUserById(String.valueOf(object));
        }
        return null;
    }

    public void setUserInfoIntoCookie(HttpServletResponse response,User user) throws UnsupportedEncodingException {
        int time = 3600*3;
        setAuthKeyIntoCookie(response,createAuthKey(user.getId(),time),time);
        setUserNickKeyIntoCookie(response,user.getName(),3600*24*30);
    }

    public void addSessionTime(String key,HttpServletResponse response){
        int time = 3600*3;
        String authKey = AuthorityCacheConst.AUTHORITY_KEY_PREFIX+key;
        redisTemplate.expire(authKey,time, TimeUnit.SECONDS);
        setAuthKeyIntoCookie(response,key,time);;
    }

    public void setAuthKeyIntoCookie(HttpServletResponse response,String key,int time){
        Cookie authKeyCookie = new Cookie(UserTokenConst.COOKIE_USER_KEY, key);
        authKeyCookie.setMaxAge(time);
        authKeyCookie.setPath("/");
        response.addCookie(authKeyCookie);
    }

    public void setUserNickKeyIntoCookie(HttpServletResponse response,String name,int time) throws UnsupportedEncodingException {
        Cookie nickCookie = new Cookie(UserTokenConst.COOKIE_NAME, URLEncoder.encode(name, "UTF-8") );
        nickCookie.setMaxAge(3600*24*30);
        nickCookie.setPath("/");
        response.addCookie(nickCookie);
    }

}
