package cn.yangtengfei.api.service.dataService.common;


import cn.yangtengfei.api.wechat.message.Article;
import cn.yangtengfei.api.wechat.message.NewsMessage;
import cn.yangtengfei.api.wechat.message.TextMessage;
import cn.yangtengfei.api.wechat.process.FormatXmlProcess;
import cn.yangtengfei.api.wechat.util.MessageUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MessageService {

    public String welcomCallBackMessage(String openId,String serverWechatId){
        NewsMessage newsMessage = new NewsMessage();
        newsMessage.setArticleCount(1);

        newsMessage.setToUserName(openId);
        newsMessage.setFromUserName(serverWechatId);
        newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
        newsMessage.setCreateTime(new Date().getTime());
        newsMessage.setFuncFlag(0);

        List<Article> articleList = new ArrayList<>();
        Article article = new Article();

        article.setDescription("你好");
        article.setTitle("欢迎新同学");
        article.setPicUrl("http://img.redocn.com/sheji/20160805/chuangyikaixuejihuanyingxintongxuehaibaosheji_6883731.jpg");
        article.setUrl("http://www.51jieguo.com/wechChat/index");
        articleList.add(article);
        newsMessage.setArticles(articleList);
        return FormatXmlProcess.newsMessageToXml(newsMessage);
    }

    public String welcomCallBackMessage1(String openId,String serverWechatId){
        TextMessage text = new TextMessage();
        text.setToUserName(openId);
        text.setFromUserName(serverWechatId);
        text.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
        text.setCreateTime(new Date().getTime());
        text.setFuncFlag(0);
        text.setContent("订阅成功,<a href='http://47.94.18.12'>QMS</a>");
        return FormatXmlProcess.textMessageToXml(text);
    }


}
