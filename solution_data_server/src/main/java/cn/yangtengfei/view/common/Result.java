package cn.yangtengfei.view.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {
    private String code;

    private String message;

    private Object data;
}
