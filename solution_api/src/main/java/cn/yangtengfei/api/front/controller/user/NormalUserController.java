package cn.yangtengfei.api.front.controller.user;


import cn.yangtengfei.api.config.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NormalUserController {


    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    public Result findById(String id){
        return null;
    }
}
