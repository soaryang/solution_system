package cn.yangtengfei.model.course;

import cn.yangtengfei.baseModel.BaseModel;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "question")
public class Course extends BaseModel {

    private String id;

    private String name;

    private String describe;

}
