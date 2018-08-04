package cn.yangtengfei.model.sourceCode;

import cn.yangtengfei.model.common.BaseModel;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;


@Document(collection = "sourceCode")
public class SourceCodeModel extends BaseModel implements Serializable{

    private String id;

    private String name;

    private String url;

    private String describe;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

}
