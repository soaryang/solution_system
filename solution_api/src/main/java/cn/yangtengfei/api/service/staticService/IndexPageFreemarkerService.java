package cn.yangtengfei.api.service.staticService;


import cn.yangtengfei.api.util.file.FreemarkerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

@Slf4j
@Service("indexPageFreemarkerService")
public class IndexPageFreemarkerService implements FreemarkerService {


    @Value("${staticFreemarkerPath}")
    private String staticFreemarkerPath;
    @Value("${staticPagePath}")
    private String staticPagePath;

    @Override
    public void createHtml(Map<String, Object> dataMap) {
        log.info("------------------create index page");
        String freemarkerPath = staticFreemarkerPath;
        //生成freemarker
        FreemarkerUtil.createFreemarker(dataMap, freemarkerPath, "index.ftl", staticPagePath + File.separator + "index.html");
    }

}
