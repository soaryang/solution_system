package cn.yangtengfei.api.controller.wechat;


import cn.yangtengfei.api.config.Result;
import cn.yangtengfei.api.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/api/wechChat")
public class WeChatController {

    private static final Logger logger = LoggerFactory.getLogger(WeChatController.class);

    @RequestMapping(value = "/getTokeken", method = RequestMethod.GET)
    public Result findById(String id){
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx35f3ce44a3e1ad2e&secret=b1f21e4d4b15be963dd58571b040515c";

        Result result = new Result();
        result.setCode("200");
        logger.info("url:"+url);
        result.setData(HttpUtil.sendGet(url,null));
        return result;
    }
}
