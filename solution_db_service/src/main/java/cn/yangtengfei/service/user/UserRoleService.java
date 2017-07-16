package cn.yangtengfei.service.user;

import cn.yangtengfei.model.user.UserRole;
import cn.yangtengfei.repository.user.UserRoleRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    public UserRole findUserRoleByUserId(String userId){
        return userRoleRepository.findByUserId(userId);
    }

    public UserRole save(UserRole userRole){
        return userRoleRepository.save(userRole);
    }


    public void del(UserRole userRole){
        userRoleRepository.delete(userRole.getId());
    }


}
