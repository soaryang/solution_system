package cn.yangtengfei.api.service.staticService;


import cn.yangtengfei.api.util.file.FreemarkerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Map;


@Slf4j
@Service("articleDetailFreemarkService")
public class ArticleDetailFreemarkService implements FreemarkerService {


    //private static final String AIM_PATH = "D:/environment/nginx/www";


    @Value("${staticFreemarkerPath}")
    private String staticFreemarkerPath;

    @Value("${staticPagePath}")
    private String staticPagePath;



    @Override
    public void createHtml(Map<String, Object> dataMap) {
        log.info("------------------create page");
        String freemarkerPath = staticFreemarkerPath;
        //生成freemarker
        FreemarkerUtil.createFreemarker(dataMap, freemarkerPath, "articleDetail.ftl", staticPagePath + File.separator + dataMap.get("id") + ".html");


    }

}
