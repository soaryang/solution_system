package cn.yangtengfei.service.question;

import cn.yangtengfei.model.question.Tag;
import cn.yangtengfei.repository.question.TagRepository;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/5/28 0028.
 */

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public Tag save(Tag tag){
        return tagRepository.save(tag);
    }

    public void del(String id){
        tagRepository.delete(id);
    }

    public Page<Tag> findByUseStatus(int useStatus,int page, int pageSize) {
        PageRequest pageRequest = new PageRequest(page, pageSize);
        return tagRepository.findByUseStatusOrderByUpdateTimeDesc(useStatus,pageRequest);
    }

    public Page<Tag> findByUseStatusOrderByQuestionCountDesc(int useStatus,int page, int pageSize) {
        PageRequest pageRequest = new PageRequest(page, pageSize);
        return tagRepository.findByUseStatusOrderByQuestionCountDesc(useStatus,pageRequest);
    }

    public Page<Tag> findByUseStatusAndNameLike(int useStatus,String name,int page, int pageSize) {
        PageRequest pageRequest = new PageRequest(page, pageSize);
        return tagRepository.findByUseStatusAndNameLikeOrderByUpdateTimeDesc(useStatus,name, pageRequest);
    }

    public Page<Tag> findByUseStatusAndNameOrderByUpdateTimeDesc(int useStatus,String name,int page, int pageSize) {
        PageRequest pageRequest = new PageRequest(page, pageSize);
        return tagRepository.findByUseStatusAndNameOrderByUpdateTimeDesc(useStatus,name, pageRequest);
    }

    public Page<Tag> findByUseStatusAndId(int useStatus,String id,int page, int pageSize) {
        PageRequest pageRequest = new PageRequest(page, pageSize);
        return tagRepository.findByUseStatusAndIdOrderByUpdateTimeDesc(useStatus,id, pageRequest);
    }

    public Page<Tag> findByUseStatusAndIdAndNameLike(int useStatus,String id,String name,int page, int pageSize) {
        PageRequest pageRequest = new PageRequest(page, pageSize);
        return tagRepository.findByUseStatusAndIdAndNameLikeOrderByUpdateTimeDesc(useStatus,id,name ,pageRequest);
    }


    public Page<Tag> findByNameLike(String name,int page, int pageSize) {
        PageRequest pageRequest = new PageRequest(page, pageSize);
        return tagRepository.findByNameLikeOrderByUpdateTimeDesc(name, pageRequest);
    }

    public Tag findById(String id){ return tagRepository.findOne(id);}

    public Tag findByName(String name){
        return tagRepository.findByName(name);
    }

    public List<Tag> findByIdIn(List<String> ids){
        return tagRepository.findByIdInOrderByUpdateTimeDesc(ids);
    }


    public List<Tag> findAll(){
        return  tagRepository.findAll();
    }

    public List<Tag> findTagWithUseStatus(int status){
        return tagRepository.findByUseStatus(status);
    }
}
