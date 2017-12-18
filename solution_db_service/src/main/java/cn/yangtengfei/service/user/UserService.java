package cn.yangtengfei.service.user;

import cn.yangtengfei.model.question.Tag;
import cn.yangtengfei.model.user.User;
import cn.yangtengfei.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User findById(String id){
        return userRepository.findOne(id);
    }


    public User save(User user){
        return userRepository.save(user);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }


    public Page<User> findAllOrderByUpdateTimeDesc(int deleteFlg, int page, int pageSize) {
        PageRequest pageRequest = new PageRequest(page, pageSize);
        return userRepository.findByDeleteFlgOrderByUpdateTimeDesc(deleteFlg,pageRequest);
    }

    public List<User> findInids(List<String> ids) {
        return userRepository.findByIdIn(ids);
    }
}
