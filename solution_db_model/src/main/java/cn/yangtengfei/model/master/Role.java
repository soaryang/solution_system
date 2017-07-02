package cn.yangtengfei.model.master;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "role")
public class Role {
    private String id;

    private String roleId;
}
