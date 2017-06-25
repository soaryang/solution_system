package cn.yangtengfei.api.exception;

import cn.yangtengfei.api.config.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
class GlobalExceptionHandler {


    @ExceptionHandler(value = CommonException.class)
    @ResponseBody
    public Result jsonErrorHandler(HttpServletRequest req, CommonException e) throws Exception {
        Result result = new Result();
        result.setMessage(e.getMessage());
        result.setCode(e.getCode());
        return result;
    }
}