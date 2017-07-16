package cn.yangtengfei.model.user;

import cn.yangtengfei.baseModel.BaseModel;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "role")
public class Role extends BaseModel {
    private String id;
    private String name;
}
