package cn.yangtengfei.model.user;

import cn.yangtengfei.baseModel.BaseModel;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.HashSet;
import java.util.Set;

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

    private Set<String> roleIds;

}
