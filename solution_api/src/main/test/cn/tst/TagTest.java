package cn.tst;

import cn.yangtengfei.Application;
import cn.yangtengfei.api.service.question.ApiTagService;
import cn.yangtengfei.api.view.question.TagView;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by Administrator on 2017/1/23.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = Application.class)
public class TagTest {

    @Autowired
    private ApiTagService apiTagService;

    @Test
    public void findParty(){
        TagView tagView = new TagView();
        tagView.setName("springboot");
        tagView = apiTagService.save(tagView);
        System.out.println("================="+ JSON.toJSONString(tagView));
    }
}
