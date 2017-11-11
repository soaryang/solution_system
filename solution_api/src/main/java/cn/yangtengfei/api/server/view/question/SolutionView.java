package cn.yangtengfei.api.server.view.question;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/28 0028.
 */

@Data
public class SolutionView implements Serializable {
    private static final long serialVersionUID = -6955301109200064719L;

    private String id;
    private String questionId;
    private String content;
}
