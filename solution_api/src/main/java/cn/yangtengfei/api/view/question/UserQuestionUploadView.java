package cn.yangtengfei.api.view.question;


import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class UserQuestionUploadView {

    @NotNull
    private List<String> tagIdList;

    @NotNull
    private String name;


    @Size(min = 1,max = 1000)
    private String describe;
}
