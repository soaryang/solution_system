package cn.yangtengfei.api.front.controller.noAutority;

import cn.yangtengfei.api.cacheService.question.SolutionCacheService;
import cn.yangtengfei.api.config.PageResultModel;
import cn.yangtengfei.api.config.Result;
import cn.yangtengfei.api.server.view.question.QuestionView;
import cn.yangtengfei.api.server.view.question.SolutionView;
import cn.yangtengfei.api.server.view.question.TagView;
import cn.yangtengfei.api.service.dataService.question.ApiQuestionService;
import cn.yangtengfei.api.service.dataService.question.ApiTagService;
import cn.yangtengfei.model.question.Question;
import cn.yangtengfei.model.question.Solution;
import cn.yangtengfei.model.question.Tag;
import cn.yangtengfei.util.ListUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/v1/api/question")
public class QuestionNoAuthorityController {

    @Autowired
    private ApiTagService apiTagService;

    @Autowired
    private ApiQuestionService apiQuestionService;

    @Autowired
    private SolutionCacheService solutionCacheService;


    @RequestMapping(value = "/listById/{pageNo}/{pageSize}/{tagId}", method = RequestMethod.GET)
    public PageResultModel findAllByTagId(@PathVariable("tagId") String tagId,@PathVariable("pageNo")Integer pageNo , @PathVariable("pageSize")Integer pageSize){
        PageResultModel pageResultModel = new PageResultModel();
        Page<Question> questionPage = apiQuestionService.findAllPageByTagId(pageNo-1,pageSize,tagId);



        List<Question> questionList = questionPage.getContent();
        if(ListUtils.checkListIsNotNull(questionList)){
            TagView tagView =  apiTagService.findById(tagId);

            List<QuestionView> questionViewList = new ArrayList<>();
            for(Question question:questionList){
                QuestionView questionView = new QuestionView();
                BeanUtils.copyProperties(question,questionView);
                questionView.setTagName(tagView.getName());
                questionViewList.add(questionView);
            }
            pageResultModel.setRows(questionViewList);
        }

        pageResultModel.setTotal(questionPage.getTotalElements());

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

    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    public Result findById(String id){
        Result result = new Result();
        QuestionView questionView = apiQuestionService.findQuestionViewById(id);
        /*if(questionView!=null){
            List<Solution> solutionList = solutionCacheService.findByQuestionIdAndDeleteFlg(questionView.getId(),0);
            List<SolutionView> solutionViewList = new ArrayList<>();
            for(Solution solution:solutionList){
                SolutionView solutionView = new SolutionView();
                BeanUtils.copyProperties(solution,solutionView);
                solutionViewList.add(solutionView);
            }
            questionView.setSolutionViewList(solutionViewList);
        }*/
        result.setCode("200");
        result.setMessage("OK");
        result.setData(questionView);
        return result;
    }

    @RequestMapping(value="/upload",method= RequestMethod.POST)
    public void hello(HttpServletRequest request, HttpServletResponse response,
                      @RequestParam(value = "editormd-image-file", required = false) MultipartFile attach) {
        try {
            request.setCharacterEncoding("utf-8");
            response.setHeader("Content-Type", "text/html");
            //String rootPath = request.getSession().getServletContext().getRealPath("/upload/editor/");

            /**
             * 文件路径不存在则需要创建文件路径
             */
            File filePath = new File("D:/temp/image");
            if (!filePath.exists()) {
                filePath.mkdirs();
            }

            //最终文件名
            File realFile = new File("D:/temp/image" + File.separator + attach.getOriginalFilename());
            FileUtils.copyInputStreamToFile(attach.getInputStream(), realFile);

            //下面response返回的json格式是editor.md所限制的，规范输出就OK
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("success",1);
            map.put("message","上传成功");
            map.put("url","http://localhost/image/"+attach.getOriginalFilename());
            response.getWriter().write(JSON.toJSONString(map));
        } catch (Exception e) {
            try {
                response.getWriter().write("{\"login\":0}");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
