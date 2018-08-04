package cn.yangtengfei.model.question;


import cn.yangtengfei.model.common.BaseModel;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "tagQuestionRelation")
public class TagQuestionRelation extends BaseModel {

    private String id;

    private String questionId;

    private String tagId;
}
