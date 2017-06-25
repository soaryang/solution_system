package cn.yangtengfei.api.view.question;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/28 0028.
 */

@Data
public class QuestionView implements Serializable {
    private static final long serialVersionUID = 1816531713207583422L;

    private String id;

    private String name;

    private String tagId;

}
