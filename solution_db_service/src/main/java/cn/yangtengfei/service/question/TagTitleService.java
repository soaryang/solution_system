package cn.yangtengfei.service.question;


import cn.yangtengfei.model.question.Tag;
import cn.yangtengfei.model.question.TagTitle;
import cn.yangtengfei.repository.question.TagTitleRespository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TagTitleService {

    @Autowired
    private TagTitleRespository tagTitleRespository;

    public List<TagTitle> findAll(){
        return tagTitleRespository.findAll();
    }

    public void save(TagTitle tagTitle){
        tagTitleRespository.save(tagTitle);
    }

    public TagTitle finById(String id){
        return tagTitleRespository.findOne(id);
    }

    public TagTitle findByTagId(String tagId){
        return tagTitleRespository.findByTagId(tagId);
    }

    public void remove(String id){
        tagTitleRespository.delete(id);
    }
}
