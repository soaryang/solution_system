package cn.yangtengfei.api.cacheService.user;


import cn.yangtengfei.cacheKey.user.UserCacheKey;
import cn.yangtengfei.model.user.User;
import cn.yangtengfei.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@Service
public class UserCacheService {

    @Autowired
    private UserService userService;

    @Cacheable(value= UserCacheKey.USER_KEY,key = "'"+UserCacheKey.USER_INFO_ID_KEY+"'+#id")
    public User findUserById(String id){
         return  userService.findById(id);
    }

    @Cacheable(value=UserCacheKey.USER_KEY, key = "'"+UserCacheKey.USER_INFO_NAME_KEY+"'+#name")
    public User findUserByName(String name){
        return  userService.findByName(name);
    }


    @Cacheable(value=UserCacheKey.USER_KEY, key = "'"+UserCacheKey.USER_INFO_EMAIL_KEY+"'+#email")
    public User findUserByEmail(String email){
        return  userService.findByEmail(email);
    }

    @Caching(put = {
            @CachePut(value = UserCacheKey.USER_KEY, key = "'"+UserCacheKey.USER_INFO_ID_KEY+"'+#user.id"),
            @CachePut(value = UserCacheKey.USER_KEY, key = "'"+UserCacheKey.USER_INFO_NAME_KEY+"'+#user.name"),
            @CachePut(value = UserCacheKey.USER_KEY, key = "'"+UserCacheKey.USER_INFO_EMAIL_KEY+"'+#user.email")
    })
    public User update(User user) {
        user =userService.save(user);
        return user;
    }

}
