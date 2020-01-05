package cn.yangtengfei.api.scheduler;

import cn.yangtengfei.api.server.pictureServer.jianshu.util.FileUtils;
import cn.yangtengfei.api.server.pictureServer.luguo.LuGuoImageService;
import cn.yangtengfei.model.question.Tag;
import cn.yangtengfei.service.article.ArticleService;
import cn.yangtengfei.service.question.QuestionService;
import cn.yangtengfei.service.question.TagService;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.*;
import java.util.List;
import java.util.Map;


@Slf4j
@Component
@Configuration
@EnableScheduling
public class TagScheduler {

	@Autowired
	private QuestionService questionService;

	@Autowired
	private TagService tagService;

	@Autowired
	private ArticleService articleService;

	@Resource
	private LuGuoImageService luGuoImageService;

	@Value("${tag.imageFilePath}")
	private String imageFilePath;

	public void tagImage() {
		log.info("计算每个标签下问题的总数");
		List<Tag> tagList = tagService.findTagWithUseStatus(1);

		//登录路边
		Map<String, String>  resultMap = Maps.newHashMap();
		try {
			resultMap = luGuoImageService.doLogin();
		} catch (IOException e) {
			e.printStackTrace();
		}
		final String token = resultMap.get("token");
		tagList.forEach(tag -> {
			try {
				Thread.sleep(5000);
				setTagImage(tag,token);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
	}


	private void setTagImage(Tag tag,String token) throws FileNotFoundException {
		if(StringUtils.isNotBlank(token)){
			String netUrl = StringUtils.EMPTY;
			try {
				String  path = imageFilePath + tag.getImagePath();
				File file = new File(path);
				InputStream inputStream =  new FileInputStream(file);

				String fileName = FileUtils.getFileName(path);
				String contentType = "image/"+FileUtils.stringSuffix(path);
				netUrl = luGuoImageService.getLuGuoImageUrlPath(token,fileName,contentType,path,inputStream);
				tag.setNetUrl(netUrl);
			}catch (Exception e){
				log.error("setSaveTagQuestionCount get imageNetUrl:{}",netUrl);
			}
		}
		tagService.save(tag);
	}

	@Description("当前标签下的问题数量")
	@Scheduled(cron="0 0/30 * * * ?")
	public void tagQuestionCount() {
		log.info("计算每个标签下问题的总数");
		List<Tag> tagList = tagService.findAll();
		tagList.forEach(tag -> setSaveTagQuestionCount(tag));

	}


	private void setSaveTagQuestionCount(Tag tag){
		long questioncount = questionService.countByTagIdAndDeleteFlg(tag.getId(),0);
		long articleCount = articleService.countByTagIdAndDeleteFlg(tag.getId(),0);
		tag.setQuestionCount(questioncount);
		tag.setArticleCount(articleCount);
		tagService.save(tag);

	}
}
