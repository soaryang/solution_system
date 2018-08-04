package cn.yangtengfei.model.course;

import cn.yangtengfei.model.common.BaseModel;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "article")
public class Article extends BaseModel {

    private String id;

    private String tagId;

    private String articleName;

    private String content;

}
