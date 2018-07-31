package cn.yangtengfei.api.server.view.question;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class SourceCodeView implements Serializable{

    private String id;

    private String link;

    private String name;

    private int age;

    private Date date;


}
