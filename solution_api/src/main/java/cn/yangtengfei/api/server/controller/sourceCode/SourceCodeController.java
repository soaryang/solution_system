package cn.yangtengfei.api.server.controller.sourceCode;

import cn.yangtengfei.api.config.RestResult;
import cn.yangtengfei.api.server.view.question.SourceCodeView;
import cn.yangtengfei.api.service.dataService.question.ApiSourceCodeService;
import cn.yangtengfei.service.sourceCode.SourceCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping(value = "/v1/api/admin/sourceCode")
public class SourceCodeController {

        @Autowired
        private SourceCodeService sourceCodeService;

        @Autowired
        private ApiSourceCodeService apiSourceCodeService;

        @RequestMapping(value="/insert",method = RequestMethod.POST)
        public RestResult insert(@ModelAttribute SourceCodeView sourceCodeView, HttpServletRequest request){
                RestResult  restResult = new RestResult();
                apiSourceCodeService.insert(sourceCodeView);
                return restResult;
        }

        @RequestMapping(value="/update",method = RequestMethod.POST)
        public RestResult update(@ModelAttribute SourceCodeView sourceCodeView, HttpServletRequest request){
                RestResult  restResult = new RestResult();
                apiSourceCodeService.update(sourceCodeView);
                return restResult;
        }

        @RequestMapping(value="/delete/{id}",method = RequestMethod.GET)
        public RestResult delete(@PathVariable("id")String id){
                RestResult  restResult = new RestResult();
                apiSourceCodeService.delete(id);
                return restResult;
        }

        @RequestMapping(value="/findById/{id}",method = RequestMethod.GET)
        public RestResult findById(@PathVariable("id")String id){
                RestResult  restResult = new RestResult();
                SourceCodeView sourceCodeView = apiSourceCodeService.findById(id);
                return restResult;
        }
}
