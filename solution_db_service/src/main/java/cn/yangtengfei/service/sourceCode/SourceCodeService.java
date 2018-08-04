package cn.yangtengfei.service.sourceCode;

import cn.yangtengfei.model.sourceCode.SourceCodeModel;
import cn.yangtengfei.repository.question.SourceCodeRespository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class SourceCodeService {

        @Autowired
        private SourceCodeRespository sourceCodeRespository;

        @Resource(name = "questionMongoTemplate")
        private MongoTemplate mongoTemplate;


        public SourceCodeModel insert(SourceCodeModel sourceCodeModel){
                return sourceCodeRespository.insert(sourceCodeModel);
        }

        public SourceCodeModel update(SourceCodeModel sourceCodeModel){
                return sourceCodeRespository.save(sourceCodeModel);
        }

        public void delete(String id){
                sourceCodeRespository.delete(id);
        }

        public void delete(SourceCodeModel sourceCodeModel){
                sourceCodeRespository.delete(sourceCodeModel);
        }

        public SourceCodeModel findById(String id){
                return sourceCodeRespository.findOne(id);
        }

        public Page<SourceCodeModel> findByDeleteFlgOrderByUpdateTimeDesc(Integer deleteFlg, int page, int pageSize){
                PageRequest pageRequest = new PageRequest(page, pageSize);
                return sourceCodeRespository.findByDeleteFlgOrderByUpdateTimeDesc(deleteFlg,pageRequest);
        }
}
