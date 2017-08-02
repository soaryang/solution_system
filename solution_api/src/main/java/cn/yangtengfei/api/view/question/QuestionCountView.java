package cn.yangtengfei.api.view.question;


import lombok.Data;

@Data
public class QuestionCountView {

    private String id;

    private String questionId;

    /**解决方案数量*/
    private long solutionCount;

    /**关注数量*/
    private long followCount;

}
