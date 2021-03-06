package cn.yangtengfei.model.question;

import cn.yangtengfei.model.common.BaseModel;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "questionCount")
public class QuestionCount extends BaseModel {

    private String id;

    private String questionId;

    /**解决方案数量*/
    private long solutionCount;

    /**关注数量*/
    private long followCount;

}
