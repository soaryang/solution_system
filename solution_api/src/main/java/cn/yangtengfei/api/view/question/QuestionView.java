package cn.yangtengfei.api.view.question;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/5/28 0028.
 */

@Data
public class QuestionView implements Serializable {
    private static final long serialVersionUID = 1816531713207583422L;

    private String id;

    private String createUserId;

    private String createUser;

    //private String createTime;

    private String name;

    private String tagId;

    private String tagName;

    private Integer solutionCount;

    private List<SolutionView> solutionViewList;

}
