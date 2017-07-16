package cn.yangtengfei.service.user;


import cn.yangtengfei.model.user.Role;
import cn.yangtengfei.repository.user.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role save(Role role){
        return roleRepository.save(role);
    }

    public Page<Role> findByDeleteFlgOrderByUpdateTimeDesc(int deleteFlg, int page, int pageSize){
        PageRequest pageRequest = new PageRequest(page, pageSize);
        return roleRepository.findByDeleteFlgOrderByUpdateTimeDesc(deleteFlg,pageRequest);
    }
}
