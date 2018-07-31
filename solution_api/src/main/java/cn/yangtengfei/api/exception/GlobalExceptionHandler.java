package cn.yangtengfei.api.exception;

import cn.yangtengfei.api.config.RestResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(value = CommonException.class)
    @ResponseBody
    public RestResult jsonErrorHandler(HttpServletRequest req, CommonException e) throws Exception {
        RestResult restResult = new RestResult();
        restResult.setMessage(e.getMessage());
        restResult.setCode(e.getCode());
        return restResult;
    }
}