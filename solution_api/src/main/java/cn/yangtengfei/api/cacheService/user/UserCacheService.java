package cn.yangtengfei.api.cacheService.user;


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

    @Cacheable(value="user",key = "'user_'+#id")
    public User findUserById(String id){
         return  userService.findById(id);
    }

    @Cacheable(value="user", key = "'user_'+#name")
    public User findUserByName(String name){
        return  userService.findByName(name);
    }

    @Caching(put = {
            @CachePut(value = "user", key = "'user_'+#user.id"),
            @CachePut(value = "user", key = "'user_'+#user.name"),
            @CachePut(value = "user", key = "'user_'+#user.email")
    })
    public User save(User user) {
        user =userService.save(user);
        return user;
    }

    /*@Caching(put = {
            @CachePut(value = "user", key = "#user.id"),
            @CachePut(value = "user", key = "#user.username"),
            @CachePut(value = "user", key = "#user.email")
    })
    public User save(User user) {
        user =userService.save(user);
        return user;
    }*/
}
