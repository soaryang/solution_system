package cn.yangtengfei.model.user;

import cn.yangtengfei.model.common.BaseModel;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Administrator on 2017/6/24 0024.
 */

@Data
@Document(collection = "userRole")
public class UserRole  extends BaseModel {

    private String id;

    private String userId;

    private String roleId;
}
