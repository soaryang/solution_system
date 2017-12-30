package cn.yangtengfei.api.service.dataService.question;

import cn.yangtengfei.api.config.Result;
import cn.yangtengfei.api.exception.CommonException;
import cn.yangtengfei.api.server.view.question.TagView;
import cn.yangtengfei.model.question.Tag;
import cn.yangtengfei.service.question.TagService;
import cn.yangtengfei.util.DateUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by Administrator on 2017/5/28 0028.
 */
@Service
@Slf4j
public class ApiTagService {

    @Autowired
    private TagService tagService;

    /*public TagView save(TagView tagView){
        Tag tag = new Tag();
        String name = tagView.getName();
        Tag tagTemp = findByName(name);
        if(tagTemp==null){
            log.info("tag不存在，执行保存");
            BeanUtils.copyProperties(tagView,tag);
            tag.setUseStatus(0);
            tag = tagService.save(tag);
            tagView.setId(tag.getId());
            return tagView;
        }else{
            log.info("tag存在,直接返回");
            BeanUtils.copyProperties(tagTemp,tagView);
        }
        return tagView;
    }*/

    public TagView  saveTag(String name,String imagePath) throws CommonException {
        Tag tagTemp = findByName(name);
        if(tagTemp!=null){
            throw new CommonException("404","名字已经存在");
        }
        Tag tag = new Tag();
        tag.setUseStatus(0);
        tag.setImagePath(imagePath);
        tag = tagService.save(tag);
        TagView tagView = new TagView();
        BeanUtils.copyProperties(tag,tagView);
        return tagView;

    }

    public TagView update(TagView tagView){
        Tag tag = new Tag();
        BeanUtils.copyProperties(tagView,tag);
        tag.setUpdateTime(DateUtils.getCurrentDate());
        tagService.save(tag);
        return tagView;
    }

    public TagView save(String tagName) throws CommonException {
        Tag tag = findByName(tagName);
        if(tag!=null){
            throw new CommonException("404","名字已经存在");
        }
        tag = new Tag();
        tag.setName(tagName);
        tag.setUseStatus(0);
        tag = tagService.save(tag);

        TagView tagView = new TagView();
        BeanUtils.copyProperties(tag,tagView);
        return tagView;
    }


    public Tag updateUseStatus(String id, Integer status,String userId){
        Tag tag = tagService.findById(id);
        log.info("要更新的tag{}", JSON.toJSONString(tag));
        if(tag==null){
            return null;
        }
        tag.setUseStatus(status);
        tag.setUpdateUserId(userId);
        tag.setUpdateTime(new Date());
        tag =tagService.save(tag);
        return tag;
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
    public Page<Tag> findByUseStatusAndNameOrderByUpdateTimeDesc(int status,String name,int page, int pageSize) {
        return tagService.findByUseStatusAndNameOrderByUpdateTimeDesc(status,name,page,pageSize);
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

    public TagView findById(String id){
        TagView tagView = new TagView();
        Tag tag =   tagService.findById(id);
        BeanUtils.copyProperties(tag,tagView);
        return tagView;
    }

}
