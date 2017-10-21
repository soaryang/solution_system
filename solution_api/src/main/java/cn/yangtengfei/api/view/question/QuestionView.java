package cn.yangtengfei.api.view.question;

import cn.yangtengfei.model.question.Tag;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/5/28 0028.
 */

@Data
public class QuestionView implements Serializable {
    private static final long serialVersionUID = 1816531713207583422L;

    private String id;

    private String createUserId;

    private Date createTime;

    private String createUser;

    //private String createTime;

    private String name;

    private String describe;

    private String tagId;

    List<Tag> tagList;

    private String tagName;

    private Integer solutionCount = 0;

    private List<SolutionView> solutionViewList;

}
