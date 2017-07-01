package cn.yangtengfei.api.service.authority;


import cn.yangtengfei.api.config.Result;
import cn.yangtengfei.api.exception.CommonException;
import cn.yangtengfei.api.util.BCrypt;
import cn.yangtengfei.api.util.ErrorCode;
import cn.yangtengfei.model.user.User;
import cn.yangtengfei.service.user.UserService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorityService {

    @Autowired
    private UserService userService;

    public Result userLogin(String userName,String password) throws Exception {
        User user = userService.findByName(userName);
        if(user==null){
            throw new CommonException(ErrorCode.Auth_Error_Code.USERNAME_IS_ERROR,"USERNAME IS ERROR");
        }
        if( !BCrypt.checkpw(password,user.getPassword())){
            throw new CommonException(ErrorCode.Auth_Error_Code.PASSWORD_IS_ERROR,"PASSWORD IS ERROR");
        }
        return null;
    }

}
