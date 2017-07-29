package cn.tst;


import cn.yangtengfei.Application;
import cn.yangtengfei.api.cacheService.question.QuestionCacheService;
import cn.yangtengfei.model.question.Question;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = Application.class)
public class QuestionTest {

    @Autowired
    private QuestionCacheService questionCacheService;
    @Test
    public void findQuestion(){
        Question question = questionCacheService.findById("595d11a31a53c91500d80cbf");
        System.out.println("==============="+ JSON.toJSONString(question));
    }
}
