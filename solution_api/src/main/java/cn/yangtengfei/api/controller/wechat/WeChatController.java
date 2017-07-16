package cn.yangtengfei.api.controller.wechat;


import cn.yangtengfei.api.config.Result;
import cn.yangtengfei.api.service.user.ApiUserService;
import cn.yangtengfei.api.util.HttpUtil;
import cn.yangtengfei.api.util.WeChatUtil;
import cn.yangtengfei.api.view.user.UserView;
import cn.yangtengfei.api.wechat.Words;
import cn.yangtengfei.api.wechat.entity.ReceiveXmlEntity;
import cn.yangtengfei.api.wechat.entity.WeixinMessage;
import cn.yangtengfei.api.wechat.message.TextMessage;
import cn.yangtengfei.api.wechat.process.FormatXmlProcess;
import cn.yangtengfei.api.wechat.process.ReceiveXmlProcess;
import cn.yangtengfei.api.wechat.util.MessageUtil;
import cn.yangtengfei.model.wechat.WechatUser;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisServer;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@RestController
@RequestMapping(value = "/wechChat")
@Slf4j
public class WeChatController {

    private static final Logger logger = LoggerFactory.getLogger(WeChatController.class);


    @Autowired
    private ApiUserService apiUserService;

    @RequestMapping(value = "/getTokeken", method = RequestMethod.GET)
    public Result findById(String id){


        Result result = new Result();
        result.setCode("200");
        result.setData(getAccessToken());
        return result;
    }

    private String getAccessToken(){
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx35f3ce44a3e1ad2e&secret=b1f21e4d4b15be963dd58571b040515c";
        String result = HttpUtil.sendGet(url,null);
        Map<String,Object> map = (Map<String,Object>) JSON.parseObject(result);
        return String.valueOf(map.get("access_token"));
    }

    @RequestMapping(value = "/valid", method = RequestMethod.GET)
    public String valid(HttpServletRequest request, HttpServletResponse response){
        // 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");

        String token = request.getParameter("token");
        if (StringUtils.isEmpty(token)) {
            token = "BTmIrnrTBeaGrB2po5gH21WWJlMeB";
        }
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if (WeChatUtil.checkSignature(signature, timestamp, nonce, token)) {
            return echostr;
        }
        return null;
    }


    @RequestMapping(value = "/valid", method = RequestMethod.POST)
    public String processWechatMag(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("valid get message-----POST");


        //String myOpenId = request.getParameter("openid");


        //String id = getAccessToken();
        //String ulr ="https://api.weixin.qq.com/cgi-bin/user/info?access_token="+id+"&openid="+myOpenId+"&lang=zh_CN";
        //logger.info("当前用户信息："+HttpUtil.sendGet(ulr,null));

        // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        /** 解析xml数据 */
        ReceiveXmlEntity xmlEntity = new ReceiveXmlProcess().getMsgEntity(request);
        log.info("valid post:" + JSON.toJSONString(xmlEntity));
        //按照touserName 得到公众帐号信息
        String wechatId = xmlEntity.getToUserName();
        String openId = xmlEntity.getFromUserName();
        String result = "";

        /*TextMessage text = new TextMessage();
        text.setToUserName(myOpenId);
        text.setFromUserName("gh_3716fa56e7f0");
        text.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
        text.setCreateTime(new Date().getTime());
        text.setFuncFlag(0);
        text.setContent(Words.WELCOME);
        result = FormatXmlProcess.textMessageToXml(text);
        return result;*/
        //消息类型为event
       if (MessageUtil.REQ_MESSAGE_TYPE_EVENT.equals(xmlEntity.getMsgType())) {
            logger.info("event------->"+xmlEntity.getEvent());
            //当用户同意允许公众账号获取地理位置时，每次打开微信公众账号，都会收到此消息
            if (MessageUtil.REQ_MESSAGE_TYPE_LOCATION.equals(xmlEntity.getEvent())) {
                log.info("LOCATION" + xmlEntity.getContent());
                //关注微信
            } else if (MessageUtil.EVENT_TYPE_SUBSCRIBE.equals(xmlEntity.getEvent())) {
                log.info("subscribe" + xmlEntity.getContent());

                UserView wechatUser = new UserView();
                wechatUser.setOpenId(openId);
                wechatUser.setSubscribeState(0);
                apiUserService.saveWechatUser(wechatUser);

                TextMessage text = new TextMessage();
                text.setToUserName(openId);
                text.setFromUserName(wechatId);
                text.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
                text.setCreateTime(new Date().getTime());
                text.setFuncFlag(0);
                text.setContent("订阅成功,<a href='http://47.94.18.12'>QMS</a>");
                result = FormatXmlProcess.textMessageToXml(text);
                //取消关注
            } else if (MessageUtil.EVENT_TYPE_UNSUBSCRIBE.equals(xmlEntity.getEvent())) {
                log.info("unsubscribe" + xmlEntity.getContent());
                //CLICK事件推送
            } else if (MessageUtil.EVENT_TYPE_CLICK.equals(xmlEntity.getEvent())) {
                log.info("CLICK" + xmlEntity.getContent());
                //view事件推送
            } else if (MessageUtil.EVENT_TYPE_VIEW.equals(xmlEntity.getEvent())) {
                log.info("VIEW" + xmlEntity.getContent());
            }
        } else if (MessageUtil.REQ_MESSAGE_TYPE_TEXT.equals(xmlEntity.getMsgType())) {
            String content = xmlEntity.getContent();
            if (!StringUtils.isEmpty(content)) {
                log.info("text" + content);
            }
        }else if (MessageUtil.REQ_MESSAGE_TYPE_VOICE.equals(xmlEntity.getMsgType())) {
           log.info("voice" + xmlEntity.getContent());

        } else if(MessageUtil.SHAKE_AROUND_USER_SHAKE.equals(xmlEntity.getMsgType())){
           log.info("ShakearoundUserShake" + xmlEntity.getContent());

        }
        return result;
    }
}
