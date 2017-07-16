package cn.yangtengfei.api.service.user;


import cn.yangtengfei.api.config.PageResultModel;
import cn.yangtengfei.api.view.user.RoleView;
import cn.yangtengfei.model.user.Role;
import cn.yangtengfei.service.user.RoleService;
import cn.yangtengfei.util.DateUtils;
import cn.yangtengfei.util.ListUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ApiRoleService {

    @Autowired
    private RoleService roleService;

    public RoleView saveRole(RoleView roleView){
        Role role = new Role();
        BeanUtils.copyProperties(roleView,role);
        Date currentDate = DateUtils.getCurrentDate();
        role.setUpdateTime(currentDate);
        role.setCreateTime(currentDate);
        role = roleService.save(role);
        roleView.setId(role.getId());
        return roleView;
    }

    public  List<RoleView> findAll(int deleteFlg){
        List<Role> roleList = roleService.findAllByDeleteFlgOrderByUpdateTimeDesc(deleteFlg);
        List<RoleView> roleViewList = new ArrayList<>();
        if(ListUtils.checkListIsNotNull(roleList)){
            for(Role role:roleList){
                RoleView roleView = new RoleView();
                BeanUtils.copyProperties(role,roleView);
                roleViewList.add(roleView);
            }
        }
        return roleViewList;

    }
    public PageResultModel<RoleView> findAll(int deleteFlg, int page, int pageSize){

        PageResultModel<RoleView> roleViewPageResultModel = new PageResultModel<RoleView>();
        Page<Role> rolePage = roleService.findByDeleteFlgOrderByUpdateTimeDesc(deleteFlg,page,pageSize);
        long count = rolePage.getTotalElements();
        List<Role> roleList = rolePage.getContent();
        List<RoleView> roleViewList = new ArrayList<>();
        if(ListUtils.checkListIsNotNull(roleList)){
            for(Role role :roleList){
                RoleView roleView = new RoleView();
                BeanUtils.copyProperties(role,roleView);
                roleViewList.add(roleView);
            }
            roleViewPageResultModel.setRows(roleViewList);
        }
        roleViewPageResultModel.setTotal(count);
        return roleViewPageResultModel;
    }
}
