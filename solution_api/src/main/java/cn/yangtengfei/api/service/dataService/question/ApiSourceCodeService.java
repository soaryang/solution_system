package cn.yangtengfei.api.service.dataService.question;


import cn.yangtengfei.api.config.PageResultModel;
import cn.yangtengfei.api.server.view.sourceCode.SourceCodeView;
import cn.yangtengfei.model.sourceCode.SourceCodeModel;
import cn.yangtengfei.service.sourceCode.SourceCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    public PageResultModel findByDeleteFlgOrderByUpdateTimeDesc(Integer deleteFlg, int page, int pageSize){
        PageResultModel pageResultModel = new PageResultModel();
        Page<SourceCodeModel> sourceCodePage = sourceCodeService.findByDeleteFlgOrderByUpdateTimeDesc(deleteFlg,page,pageSize);
        pageResultModel.setTotal(sourceCodePage.getTotalElements());
        pageResultModel.setRows(sourceCodePage.getContent());
        return pageResultModel;
    }
}
