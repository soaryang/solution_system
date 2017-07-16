package cn.yangtengfei.api.service.user;

import cn.yangtengfei.api.config.PageResultModel;
import cn.yangtengfei.api.view.user.UserView;
import cn.yangtengfei.model.user.User;
import cn.yangtengfei.model.wechat.WechatUser;
import cn.yangtengfei.service.user.UserService;
import cn.yangtengfei.service.wechat.WechatUserService;
import cn.yangtengfei.util.DateUtils;
import cn.yangtengfei.util.ListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Administrator on 2017/5/28 0028.
 */

@Service
public class ApiUserService {

    private static final Logger logger = LoggerFactory.getLogger(ApiUserService.class);


    @Autowired
    private UserService userService;

    @Autowired
    private WechatUserService wechatUserService;

    public UserView findByOpenId(String openId){
        WechatUser wechatUser =  wechatUserService.findByOpenId(openId);
        UserView userView = new UserView();
        BeanUtils.copyProperties(wechatUser,userView);
        return userView;
    }

    public void saveWechatUser(UserView userView){
        String openId = userView.getOpenId();
        WechatUser wechatUser =  wechatUserService.findByOpenId(openId);
        User user = new User();
        Date currentDate =  DateUtils.getCurrentDate();
        if(wechatUser==null){
            user.setCreateTime(currentDate);
            user.setUpdateTime(currentDate);
            user = userService.save(user);

            wechatUser = new WechatUser();
            wechatUser.setUserId(user.getId());
            wechatUser.setOpenId(openId);
            wechatUser.setSubscribeState(userView.getSubscribeState());
            wechatUser.setCreateTime(currentDate);
            wechatUser.setUpdateTime(currentDate);
            wechatUserService.save(wechatUser);
        }else{
            wechatUser.setSubscribeState(userView.getSubscribeState());
            wechatUser.setUpdateTime(currentDate);
            wechatUserService.save(wechatUser);
        }
    }

    public PageResultModel<UserView> findPage(int deleteFlg,int index,int pageSize){
        PageResultModel<UserView> userViewPageResultModel = new PageResultModel<UserView>();
        Page<WechatUser> wechatUserPage = wechatUserService.findWechatUserPage(0,0,index,pageSize);
        List<WechatUser> wechatUserList = wechatUserPage.getContent();
        long count  = wechatUserPage.getTotalElements();
        List<String> ids = new ArrayList<>();
        List<UserView> userViewList = new ArrayList<UserView>();
        if(ListUtils.checkListIsNotNull(wechatUserList)){
            for(WechatUser wechatUser:wechatUserList){
                ids.add(wechatUser.getUserId());
            }
            List<User> userList = userService.findInids(ids);
            Map<String,User> stringUserMap = new HashMap<String,User>();
            if(ListUtils.checkListIsNotNull(userList)){
                for(User user:userList){
                    stringUserMap.put(user.getId(),user);
                }
            }
            for(WechatUser wechatUser:wechatUserList){
                String userId = wechatUser.getUserId();
                UserView userView = new UserView();
                User user = stringUserMap.get(userId);
                BeanUtils.copyProperties(user,userView);
                userView.setId(userId);
                userView.setSubscribeState(wechatUser.getSubscribeState());
                userView.setOpenId(wechatUser.getOpenId());
                userViewList.add(userView);
            }
        }
        userViewPageResultModel.setRows(userViewList);
        userViewPageResultModel.setTotal(count);
        return userViewPageResultModel;
    }
}
