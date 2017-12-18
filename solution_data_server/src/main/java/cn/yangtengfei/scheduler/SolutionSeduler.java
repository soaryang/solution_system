package cn.yangtengfei.scheduler;

import cn.yangtengfei.model.question.Question;
import cn.yangtengfei.model.question.Tag;
import cn.yangtengfei.service.question.TagService;
import cn.yangtengfei.util.LogicBeanUtil;
import cn.yangtengfei.view.tag.TagView;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Configuration
@EnableScheduling
@Slf4j
public class SolutionSeduler {

    @Value("${file.TagFilePath}")
    private String tagFilecacheFath;

    @Autowired
    private TagService tagService;

    @Scheduled(cron = "0 0/1 * * * *")
    public void createTagCacheFile() {
        log.info("createQuestionCache------------start");
        Page<Tag> page = tagService.findByUseStatus(0,0,12);
        List<TagView> tagViewList = new ArrayList<TagView>();
        if(page.getContent()!=null){

            tagViewList = LogicBeanUtil.copyListToAimList(page.getContent(),tagViewList);
            log.info("tagViewList:{}",JSON.toJSONString(tagViewList));
            try {
                FileUtils.writeStringToFile(new File(tagFilecacheFath), JSON.toJSONString(tagViewList), "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
