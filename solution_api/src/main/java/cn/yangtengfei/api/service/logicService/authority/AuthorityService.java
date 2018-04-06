package cn.yangtengfei.api.service.logicService.authority;


import cn.yangtengfei.api.cacheService.authority.AuthorityCacheService;
import cn.yangtengfei.api.cacheService.user.UserCacheService;
import cn.yangtengfei.api.config.RedisService;
import cn.yangtengfei.api.exception.CommonException;
import cn.yangtengfei.api.service.dataService.user.ApiUserService;
import cn.yangtengfei.api.util.BCrypt;
import cn.yangtengfei.api.util.ErrorCode;
import cn.yangtengfei.api.util.cont.cacheConst.UserCacheConst;
import cn.yangtengfei.api.server.view.user.UserView;
import cn.yangtengfei.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
public class AuthorityService {

    @Autowired
    private UserCacheService userCacheService;

    @Autowired
    private ApiUserService apiUserService;

    @Autowired
    private AuthorityCacheService authorityCacheService;

    @Autowired
    private RedisService redisService;


    public User userLogin(String userName,String password,HttpServletResponse response) throws Exception {
        User user = userCacheService.findUserByName(userName);
        if(user==null){
            throw new CommonException(ErrorCode.Auth_Error_Code.USERNAME_IS_ERROR,"USERNAME IS ERROR");
        }

        UserView userView = apiUserService.finUserInfo(user.getId());

        Object object = redisService.get(UserCacheConst.USER_PASSWORD_CACHE_KEY+userView.getOpenId());

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
        authorityCacheService.setAdminUserInfoIntoCookie(response,user);
        return user;
    }


}
