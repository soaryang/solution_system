package cn.yangtengfei.api.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/28 0028.
 */

@JsonSerialize
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result implements Serializable {

    private static final long serialVersionUID = -332320156501537998L;

    private String code;

    private String message;

    private Object data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
