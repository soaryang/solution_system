package cn.yangtengfei.api.exception;

public class CommonException extends Exception {

    private String code;

    public CommonException(String message) {
        super(message);
    }

    public CommonException(String code, String message) {
        super(message);
        this.code = code;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}