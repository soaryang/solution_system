package cn.yangtengfei.api.service.question;

import cn.yangtengfei.api.view.question.TagView;
import cn.yangtengfei.model.question.Tag;
import cn.yangtengfei.service.question.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/5/28 0028.
 */
@Service
public class ApiTagService {

    @Autowired
    private TagService tagService;

    public TagView save(TagView tagView){
        Tag questionType = new Tag();
        BeanUtils.copyProperties(tagView,questionType);
        questionType = tagService.save(questionType);
        tagView.setId(questionType.getId());
        return tagView;
    }

    public void save(String tagName){
        Tag tag = new Tag();
        tag.setName(tagName);
        tagService.save(tag);
    }

    public void del(String id){
        tagService.del(id);
    }

    public Page<Tag> findAllPage(int page, int pageSize) {
        return tagService.findAllPage(page,pageSize);
    }
    public Page<Tag> findAllPageByName(String name,int page, int pageSize) {
        return tagService.findAllPageByName(name,page,pageSize);
    }

    public Tag findByName(String name){
        return tagService.findByName(name);
    }

}
