package cn.yangtengfei.api.server.controller.question;

import cn.yangtengfei.api.config.PageResultModel;
import cn.yangtengfei.api.config.Result;
import cn.yangtengfei.api.server.controller.base.BaseController;
import cn.yangtengfei.api.service.dataService.question.ApiTagService;
import cn.yangtengfei.api.server.view.question.TagView;
import cn.yangtengfei.model.question.Tag;
import cn.yangtengfei.webCrawler.stackOverFlow.StacKOverFlowDataCrwaler;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Administrator on 2017/5/28 0028.
 */
@RestController
@RequestMapping(value = "/v1/api/admin/tag")
@Slf4j
public class TagController extends BaseController {

    @Autowired
    private ApiTagService apiTagService;

    @Autowired
    private StacKOverFlowDataCrwaler stacKOverFlowDataCrwaler;





    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    public Result findAll(String id){
        Result result = new Result();
        result.setCode("200");
        result.setData(apiTagService.findById(id));
        return result;
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public PageResultModel findAll(HttpServletRequest request,Integer pageNumber , Integer pageSize){
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("759620299@qq.com");
//        message.setTo("252276549@qq.com");
//        message.setSubject("主题：简单邮件");
//        message.setText("测试邮件内容");
//        mailSender.send(message);

        String id = request.getParameter("id");
        String name = request.getParameter("name");
        int useStatus = Integer.parseInt(request.getParameter("useStatus"));
        int start = pageNumber-1;
        PageResultModel pageResultModel = new PageResultModel();
        Page<Tag> questionTypes = null;
        if(StringUtils.isEmpty(id) && StringUtils.isEmpty(name)){
            questionTypes = apiTagService.findByUseStatus(useStatus,start,pageSize);
        }else  if(!StringUtils.isEmpty(id) && StringUtils.isEmpty(name)){
            questionTypes = apiTagService.findByUseStatusAndId(useStatus,id,start,pageSize);
        }else  if(StringUtils.isEmpty(id) && !StringUtils.isEmpty(name)){
            questionTypes = apiTagService.findByUseStatusAndNameLike(useStatus,name,start,pageSize);
        }else {
            questionTypes = apiTagService.findByUseStatusAndIdAndNameLike(useStatus,id,name,start,pageSize);
        }
        pageResultModel.setTotal(questionTypes.getTotalElements());
        pageResultModel.setRows(questionTypes.getContent());
        return  pageResultModel;
    }

    @RequestMapping(value = "/page/{tagName}", method = RequestMethod.GET)
    public PageResultModel findAllByTagName(@PathVariable("tagName") String tagName){
        PageResultModel pageResultModel = new PageResultModel();
        Page<Tag> questionTypes = apiTagService.findByNameLike(tagName,0,10);
        pageResultModel.setTotal(questionTypes.getTotalElements());
        pageResultModel.setRows(questionTypes.getContent());
        return  pageResultModel;
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(@ModelAttribute TagView tagView, HttpServletRequest request){
        log.info("保存tag:{}",JSON.toJSONString(tagView));
        Result result = new Result();
        tagView = apiTagService.save(tagView);
        result.setCode("200");
        result.setData(tagView);
        return result;
    }


    @RequestMapping(value = "/tagUse", method = RequestMethod.GET)
    public Result tagUse(String id,Integer status, HttpServletRequest request){
        apiTagService.updateUseStatus(id,status,getUser().getId());
        Result result = new Result();
        result.setCode("200");
        result.setMessage("OK");
        return result;
    }


    @RequestMapping(value = "/del/{id}", method = RequestMethod.GET)
    public Result del(@PathVariable("id") String id){
        Result result = new Result();
        apiTagService.del(id);
        result.setCode("200");
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
        result.setCode("200");
        result.setMessage("OK");
        return  result;
    }

}
