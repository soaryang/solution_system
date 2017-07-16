package cn.yangtengfei.service.wechat;


import cn.yangtengfei.model.wechat.WechatUser;
import cn.yangtengfei.repository.user.WechatRepostitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class WechatUserService {

   // @Autowired
   // private WechatRepostitory wechatRepostitory;
    @Autowired
    private WechatRepostitory wechatRepostitory;

    public Page<WechatUser> findWechatUserPage(int subscribeState,int deleteFlg, int page, int pageSize){
        PageRequest pageRequest = new PageRequest(page, pageSize);
       return wechatRepostitory.findBySubscribeStateAndDeleteFlgOrderByUpdateTimeDesc(subscribeState,deleteFlg,pageRequest);
    }

    public WechatUser save(WechatUser wechatUser){
       return wechatRepostitory.save(wechatUser);
    }

    public WechatUser findByUserId(String openId){
        return wechatRepostitory.findByUserId(openId);
    }


    public WechatUser findByOpenId(String openId){
        return wechatRepostitory.findByOpenId(openId);
    }


}
