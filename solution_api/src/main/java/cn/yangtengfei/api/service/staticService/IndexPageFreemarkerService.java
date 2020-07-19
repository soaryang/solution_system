package cn.yangtengfei.api.service.staticService;


import cn.yangtengfei.api.util.file.FreemarkerUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

@Service("indexPageFreemarkerService")
public class IndexPageFreemarkerService implements FreemarkerService {


    private static final String TEMPLATE_PATH = "templates";
    private static final String AIM_PATH = "D:/environment/nginx/www";

    @Override
    public void createHtml(Map<String, Object> dataMap) {
        try {
            String freemarkerPath = ResourceUtils.getURL("classpath:").getPath() + File.separator + TEMPLATE_PATH;
            //生成freemarker
            FreemarkerUtil.createFreemarker(dataMap, freemarkerPath, "index.ftl", AIM_PATH + File.separator + "index.html");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
