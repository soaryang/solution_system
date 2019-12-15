package cn.yangtengfei.api.server.view.question;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/28 0028.
 */

@Data
public class TagView implements Serializable {

    private static final long serialVersionUID = -6827908880400309748L;

    private String id;

    private String name;

    private String imagePath;

    private String describe;

    /**未使用：0 ; 使用：1 ；禁用：2*/
    private Integer useStatus;

    private int questionCount;

    //图片来源
    private int pictureSource;
    //图片的地址
    private String path;
}
