package cn.yangtengfei.api.front.view.article;

import cn.yangtengfei.model.baseModel.BaseModel;
import lombok.Data;

@Data
public class ArticleView extends BaseModel {

    private String id;

    private String tagId;

    private String articleName;

    private String content;

    private String tagName;

}
