package cn.yangtengfei.api.controller.question;

import cn.yangtengfei.api.config.PageResultModel;
import cn.yangtengfei.api.config.Result;
import cn.yangtengfei.api.controller.base.BaseController;
import cn.yangtengfei.api.service.question.ApiTagService;
import cn.yangtengfei.api.view.question.TagView;
import cn.yangtengfei.model.question.Question;
import cn.yangtengfei.model.question.Tag;
import cn.yangtengfei.webCrawler.stackOverFlow.StacKOverFlowDataCrwaler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Administrator on 2017/5/28 0028.
 */
@RestController
@RequestMapping(value = "/v1/api/admin/tag")
public class TagController extends BaseController {

    @Autowired
    private ApiTagService apiTagService;

    @Autowired
    private StacKOverFlowDataCrwaler stacKOverFlowDataCrwaler;

    @Autowired
    private JavaMailSender mailSender;

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public PageResultModel findAll(Integer pageNumber , Integer pageSize){
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("759620299@qq.com");
//        message.setTo("252276549@qq.com");
//        message.setSubject("主题：简单邮件");
//        message.setText("测试邮件内容");
//        mailSender.send(message);

        PageResultModel pageResultModel = new PageResultModel();
        Page<Tag> questionTypes = apiTagService.findAllPage(pageNumber-1,pageSize);
        pageResultModel.setTotal(questionTypes.getTotalElements());
        pageResultModel.setRows(questionTypes.getContent());
        return  pageResultModel;
    }

    @RequestMapping(value = "/page/{tagName}", method = RequestMethod.GET)
    public PageResultModel findAllByTagName(@PathVariable("tagName") String tagName){
        PageResultModel pageResultModel = new PageResultModel();
        Page<Tag> questionTypes = apiTagService.findAllPageByName(tagName,0,10);
        pageResultModel.setTotal(questionTypes.getTotalElements());
        pageResultModel.setRows(questionTypes.getContent());
        return  pageResultModel;
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(@ModelAttribute TagView tagView, HttpServletRequest request){
        return null;
    }

    @RequestMapping(value = "/del/{id}", method = RequestMethod.GET)
    public Result del(@PathVariable("id") String id){
        Result result = new Result();
        apiTagService.del(id);
        result.setCode(200);
        result.setMessage("OK");
        return  result;
    }

    @RequestMapping(value = "/crawler", method = RequestMethod.GET)
    public Result crawler(){
        Result result = new Result();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                int size = 2;
                while (i < 1440) {

                    int end = i+size;
                    int begin =i+1;
                    i = i+size;
                    List<String> tagList = stacKOverFlowDataCrwaler.getTagList(begin,end);
                    if(tagList!=null && tagList.size()>0){
                        System.out.println("获取tag的数量是:{}"+tagList.size());
                        for(String tag:tagList){
                            Tag tagObject = apiTagService.findByName(tag);
                            if(tagObject==null){
                                apiTagService.save(tag);
                            }
                        }
                    }
                }

            }
        }).start();
        result.setCode(200);
        result.setMessage("OK");
        return  result;
    }

}
