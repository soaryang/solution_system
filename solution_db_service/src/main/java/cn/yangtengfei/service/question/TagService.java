package cn.yangtengfei.service.question;

import cn.yangtengfei.model.question.Tag;
import cn.yangtengfei.repository.question.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/5/28 0028.
 */

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public Tag save(Tag questionType){
        return tagRepository.save(questionType);
    }

    public void del(String id){
        tagRepository.delete(id);
    }

    public Page<Tag> findByUseStatus(int useStatus,int page, int pageSize) {
        Sort sort = new Sort(Sort.Direction.ASC, "createTime");
        PageRequest pageRequest = new PageRequest(page, pageSize, sort);
        return tagRepository.findByUseStatus(useStatus,pageRequest);
    }

    public Page<Tag> findByUseStatusAndNameLike(int useStatus,String name,int page, int pageSize) {
        Sort sort = new Sort(Sort.Direction.ASC, "createTime");
        PageRequest pageRequest = new PageRequest(page, pageSize, sort);
        return tagRepository.findByUseStatusAndNameLike(useStatus,name, pageRequest);
    }

    public Page<Tag> findByUseStatusAndId(int useStatus,String id,int page, int pageSize) {
        Sort sort = new Sort(Sort.Direction.ASC, "createTime");
        PageRequest pageRequest = new PageRequest(page, pageSize, sort);
        return tagRepository.findByUseStatusAndId(useStatus,id, pageRequest);
    }

    public Page<Tag> findByUseStatusAndIdAndNameLike(int useStatus,String id,String name,int page, int pageSize) {
        Sort sort = new Sort(Sort.Direction.ASC, "createTime");
        PageRequest pageRequest = new PageRequest(page, pageSize, sort);
        return tagRepository.findByUseStatusAndIdAndNameLike(useStatus,id,name ,pageRequest);
    }


    public Page<Tag> findByNameLike(String name,int page, int pageSize) {
        Sort sort = new Sort(Sort.Direction.ASC, "createTime");
        PageRequest pageRequest = new PageRequest(page, pageSize, sort);
        return tagRepository.findByNameLike(name, pageRequest);
    }

    public Tag findById(String id){ return tagRepository.findOne(id);}

    public Tag findByName(String name){
        return tagRepository.findByName(name);
    }
}
