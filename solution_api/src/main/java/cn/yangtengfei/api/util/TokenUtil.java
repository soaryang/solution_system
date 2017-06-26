package cn.yangtengfei.api.util;

import cn.yangtengfei.model.user.User;
import com.alibaba.fastjson.JSON;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class TokenUtil {


    public static String  createAuthKey(String id){
        String hashKey = BCrypt.hashpw(id,BCrypt.gensalt());
        return hashKey;
    }
}
