package cn.yangtengfei.api.service.staticService;


import cn.yangtengfei.api.util.file.FreemarkerUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

@Service("articleDetailFreemarkService")
public class ArticleDetailFreemarkService implements FreemarkerService {

    private static final String TEMPLATE_PATH = "templates";

    //private static final String AIM_PATH = "D:/environment/nginx/www";


    @Value("${staticPagePath}")
    private String staticPagePath;

    @Override
    public void createHtml(Map<String, Object> dataMap) {
        try {

            String freemarkerPath = ResourceUtils.getURL("classpath:").getPath() + File.separator + TEMPLATE_PATH;

            //生成freemarker
            FreemarkerUtil.createFreemarker(dataMap, freemarkerPath, "articleDetail.ftl", staticPagePath + File.separator + dataMap.get("id") + ".html");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
