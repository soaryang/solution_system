package cn.yangtengfei.api.server.controller.wechat;


import cn.yangtengfei.api.config.RedisService;
import cn.yangtengfei.api.config.RestResult;
import cn.yangtengfei.api.server.view.user.UserView;
import cn.yangtengfei.api.service.dataService.common.MessageService;
import cn.yangtengfei.api.service.dataService.user.ApiUserService;
import cn.yangtengfei.api.util.BCrypt;
import cn.yangtengfei.api.util.HttpUtil;
import cn.yangtengfei.api.util.RandomCodeUtil;
import cn.yangtengfei.api.util.WeChatUtil;
import cn.yangtengfei.api.util.cont.cacheConst.UserCacheConst;
import cn.yangtengfei.api.wechat.entity.ReceiveXmlEntity;
import cn.yangtengfei.api.wechat.message.TextMessage;
import cn.yangtengfei.api.wechat.process.FormatXmlProcess;
import cn.yangtengfei.api.wechat.process.ReceiveXmlProcess;
import cn.yangtengfei.api.wechat.util.MessageUtil;
import cn.yangtengfei.model.question.Tag;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping(value = "/wechChat")
@Slf4j
public class WeChatPageController {


	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String findTags(){
		return "index";
	}
}
