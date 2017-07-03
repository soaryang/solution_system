package cn.yangtengfei.api.service.question;

import cn.yangtengfei.api.view.question.TagView;
import cn.yangtengfei.model.question.Tag;
import cn.yangtengfei.service.question.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;

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


    public void updateUseStatus(String id, Integer status,String userId){
        Tag tag = tagService.findById(id);
        if(tag!=null){
            tag.setUseStatus(status);
            tag.setUpdateUserId(userId);
            tag.setUpdateTime(new Date());
        }
        tagService.save(tag);
    }

    public void del(String id){
        tagService.del(id);
    }

    public Page<Tag> findByUseStatus(int status,int page, int pageSize) {
        return tagService.findByUseStatus(status,page,pageSize);
    }

    public Page<Tag> findByUseStatusAndNameLike(int status,String name,int page, int pageSize) {
        return tagService.findByUseStatusAndNameLike(status,name,page,pageSize);
    }

    public Page<Tag> findByUseStatusAndId(int status,String id,int page, int pageSize) {
        return tagService.findByUseStatusAndId(status,id,page,pageSize);
    }

    public Page<Tag> findByUseStatusAndIdAndNameLike(int status,String id,String name,int page, int pageSize) {
        return tagService.findByUseStatusAndIdAndNameLike(status,id,name,page,pageSize);
    }

    public Page<Tag> findByNameLike(String name,int page, int pageSize) {
        return tagService.findByNameLike(name,page,pageSize);
    }

    public Tag findByName(String name){
        return tagService.findByName(name);
    }

}
