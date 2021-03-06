package cn.yangtengfei.api.server.controller.question;

import cn.yangtengfei.api.config.PageResultModel;
import cn.yangtengfei.api.config.RestResult;
import cn.yangtengfei.api.server.controller.base.BaseController;
import cn.yangtengfei.api.service.dataService.question.ApiSolutionService;
import cn.yangtengfei.api.server.view.question.SolutionView;
import cn.yangtengfei.model.question.Solution;
import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/28 0028.
 */

@RestController
@RequestMapping(value = "/v1/api/admin/solution")
public class SolutionController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(SolutionController.class);

    @Autowired
    private ApiSolutionService apiSolutionService;

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public PageResultModel findAll(Integer pageNumber , Integer pageSize, String questionId){
        PageResultModel pageResultModel = new PageResultModel();
        Page<Solution> solutionPage =  apiSolutionService.findAllByQuestionId(pageNumber-1,pageSize,questionId);
        pageResultModel.setTotal(solutionPage.getTotalElements());
        pageResultModel.setRows(solutionPage.getContent());
        return pageResultModel;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public RestResult save(@ModelAttribute SolutionView solutionView, HttpServletRequest request){
        //return  null;
        apiSolutionService.save(solutionView);
        return null;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public RestResult edit(@ModelAttribute SolutionView solutionView, HttpServletRequest request){
        //return  null;
        RestResult restResult = new RestResult();
        restResult.setCode("200");
        restResult.setData(apiSolutionService.save(solutionView));
        return restResult;
    }

    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public RestResult del(String id){
        //return  null;
        RestResult restResult = new RestResult();
        apiSolutionService.del(id);
        restResult.setCode("200");
        return restResult;
    }


    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    public RestResult findById(String id){
        //return  null;
        RestResult restResult = new RestResult();
        SolutionView solutionView = apiSolutionService.findSolutionViewById(id);
        restResult.setCode("200");
        restResult.setData(solutionView);
        return restResult;
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
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("login", 1);
            map.put("message", "上传成功");
            map.put("url", "http://localhost/image/" + attach.getOriginalFilename());
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
