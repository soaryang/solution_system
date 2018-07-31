package cn.yangtengfei.model.user;


import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "userAuthority")
public class UserAuthority {

    private String id;

    private String userId;

    private String url;
}
