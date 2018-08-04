package cn.yangtengfei.model.user;

import cn.yangtengfei.model.common.BaseModel;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Administrator on 2017/5/28 0028.
 */

@Data
@Document(collection = "user")
public class User extends BaseModel {

    private String id;

    private String name;

    private String email;

    private String password;

    /**注册来源 1：微信 2:github*/
    private Integer userRegisterType;

}
