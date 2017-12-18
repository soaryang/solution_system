package cn.yangtengfei.api.view.question;

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

    private int questionCount;
}
