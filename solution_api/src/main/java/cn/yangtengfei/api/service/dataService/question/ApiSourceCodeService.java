package cn.yangtengfei.api.service.dataService.question;


import cn.yangtengfei.api.server.view.question.SourceCodeView;
import cn.yangtengfei.model.sourceCode.SourceCodeModel;
import cn.yangtengfei.service.sourceCode.SourceCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ApiSourceCodeService {

        @Autowired
        private SourceCodeService sourceCodeService;

        public void insert(SourceCodeView sourceCodeView){
            SourceCodeModel sourceCodeModel = new SourceCodeModel();
            BeanUtils.copyProperties(sourceCodeView,sourceCodeModel);
            sourceCodeService.insert(sourceCodeModel);
        }

        public void update(SourceCodeView sourceCodeView){
            SourceCodeModel sourceCodeModel = new SourceCodeModel();
            BeanUtils.copyProperties(sourceCodeView,sourceCodeModel);
            sourceCodeService.update(sourceCodeModel);
        }

        public void delete(String id){
            sourceCodeService.delete(id);
        }

        public void delete(SourceCodeView sourceCodeView){
            SourceCodeModel sourceCodeModel = new SourceCodeModel();
            BeanUtils.copyProperties(sourceCodeView,sourceCodeModel);
            sourceCodeService.delete(sourceCodeModel);
        }

        public SourceCodeView findById(String id){
            SourceCodeModel sourceCodeModel = sourceCodeService.findById(id);
            SourceCodeView sourceCodeView = null;
            if(sourceCodeModel!=null){
                sourceCodeView = new SourceCodeView();
                BeanUtils.copyProperties(sourceCodeModel,sourceCodeView);
            }
            return sourceCodeView;
        }
}
