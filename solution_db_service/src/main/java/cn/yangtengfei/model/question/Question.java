package cn.yangtengfei.model.question;

import cn.yangtengfei.model.common.BaseModel;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Administrator on 2017/5/28 0028.
 */

@Data
@Document(collection = "question")
public class Question extends BaseModel{

    private String id;

    private String name;

    private String tagId;

    private String describe;

}
