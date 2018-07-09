package cn.yangtengfei.api.server.view.question;


import lombok.Data;

@Data
public class TagTitleView {


    public TagTitleView(String id, String tagId, String tagName) {
        this.id = id;
        this.tagId = tagId;
        this.tagName = tagName;
    }

    private String id;

    private String tagId;

    private String tagName;

}
