package cn.yangtengfei.service;

import cn.yangtengfei.view.common.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/v1/api/index")
public class IndexService {

    @RequestMapping(value = "/initData", method = RequestMethod.GET)
    public Result indexData(){
        return null;
    }
}
