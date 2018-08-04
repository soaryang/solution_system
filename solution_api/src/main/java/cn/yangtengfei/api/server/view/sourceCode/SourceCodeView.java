package cn.yangtengfei.api.server.view.sourceCode;


import lombok.Data;

import java.io.Serializable;


@Data
public class SourceCodeView implements Serializable{

    private String id;

    private String name;

    private String url;

    private String describe;


}
