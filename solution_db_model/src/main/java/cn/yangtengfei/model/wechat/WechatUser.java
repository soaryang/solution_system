package cn.yangtengfei.model.wechat;


import cn.yangtengfei.baseModel.BaseModel;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "wechatUser")
public class WechatUser  extends BaseModel {

    private String id;

    private String userId;

    private String nick;

    private String imgUrl;

    private String openId;

    private Integer subscribeState;
}
