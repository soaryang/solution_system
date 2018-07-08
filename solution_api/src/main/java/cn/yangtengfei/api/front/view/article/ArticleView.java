package cn.yangtengfei.api.front.view.article;

import cn.yangtengfei.baseModel.BaseModel;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
public class ArticleView extends BaseModel {

    private String id;

    private String tagId;

    private String articleName;

    private String content;

    private String tagName;

}
