package cn.yangtengfei.service.wechat;


import cn.yangtengfei.model.wechat.WechatUser;
import cn.yangtengfei.repository.wechat.WechatRepostitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class WechatUserService {

    @Autowired
    private WechatRepostitory wechatRepostitory;

    public Page<WechatUser> findWechatUserPage(int subscribeState,int deleteFlg, int page, int pageSize){
        PageRequest pageRequest = new PageRequest(page, pageSize);
        return wechatRepostitory.findBysubScribeStateAndDeleteFlgOrderByUpdateTimeDesc(subscribeState,deleteFlg,pageRequest);
    }

    public WechatUser save(WechatUser wechatUser){
        return wechatRepostitory.save(wechatUser);
    }
}
