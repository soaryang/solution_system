package cn.yangtengfei.api.service.dataService.common;

import cn.yangtengfei.api.wechat.message.Article;
import cn.yangtengfei.api.wechat.message.NewsMessage;
import cn.yangtengfei.api.wechat.process.FormatXmlProcess;
import cn.yangtengfei.api.wechat.util.MessageUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MessageContentService {

	public String sendMessage(String openId, String serverWechatId, List<Article> articleList) {
		NewsMessage newsMessage = new NewsMessage();
		newsMessage.setArticleCount(articleList.size());
		newsMessage.setToUserName(openId);
		newsMessage.setFromUserName(serverWechatId);
		newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setFuncFlag(0);
		newsMessage.setArticles(articleList);
		return FormatXmlProcess.newsMessageToXml(newsMessage);
	}
}
