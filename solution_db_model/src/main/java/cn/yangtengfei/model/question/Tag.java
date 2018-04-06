package cn.yangtengfei.model.question;

import cn.yangtengfei.baseModel.BaseModel;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by Administrator on 2017/5/28 0028.
 */

@Data
@Document(collection = "tag")
public class Tag extends BaseModel {

    private String id;

    private String name;

    private String imagePath;

    private String describe;

    /**未使用：0 ; 使用：1 ；禁用：2*/
    private Integer useStatus;

    /**解决方案数量*/
    private long questionCount;

}
