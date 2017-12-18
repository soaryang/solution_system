package cn.yangtengfei.api.server.view.question;


import lombok.Data;

@Data
public class QuestionCountView {

    private String id;


    /**解决方案数量*/
    private long solutionCount;

    /**关注数量*/
    private long followCount;

}
