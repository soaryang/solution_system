package cn.yangtengfei.api.front.view.question;


import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class UserQuestionUploadView {

    private List<String> tagInfo;

    private String name;

    private String describe;
}


