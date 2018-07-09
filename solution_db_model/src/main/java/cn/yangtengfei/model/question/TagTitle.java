package cn.yangtengfei.model.question;


import cn.yangtengfei.baseModel.BaseModel;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "tagTitle")
public class TagTitle extends BaseModel {

    private String id;

    private String tagId;

}
