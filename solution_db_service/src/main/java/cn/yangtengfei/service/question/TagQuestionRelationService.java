package cn.yangtengfei.service.question;

import cn.yangtengfei.model.question.TagQuestionRelation;
import cn.yangtengfei.repository.question.TagQuestionRelationRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagQuestionRelationService {

    @Autowired
    private TagQuestionRelationRespository tagQuestionRelationRespository;

    public void save(TagQuestionRelation tagQuestionRelation){
        tagQuestionRelationRespository.save(tagQuestionRelation);
    }

    public void deleteByQuestionId(String questionId){
        tagQuestionRelationRespository.deleteByQuestionId(questionId);
    }
}
