package cn.yangtengfei.service.user;

import cn.yangtengfei.model.user.User;
import cn.yangtengfei.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/5/28 0028.
 */

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public User findByName(String name){
        return userRepository.findByName(name);
    }
}
