package cn.yangtengfei.view.tag;

import lombok.Data;

@Data
public class TagView {

    private String id;

    private String name;

    private String url;

    /**未使用：0 ; 使用：1 ；禁用：2*/
    private Integer useStatus;

}
