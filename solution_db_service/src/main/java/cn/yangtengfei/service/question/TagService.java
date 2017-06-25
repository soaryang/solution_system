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

    public Page<Tag> findAllPage(int page, int pageSize) {
        Sort sort = new Sort(Sort.Direction.ASC, "createTime");
        PageRequest pageRequest = new PageRequest(page, pageSize, sort);
        return tagRepository.findAll(pageRequest);
    }

    public Page<Tag> findAllPageByName(String name,int page, int pageSize) {
        Sort sort = new Sort(Sort.Direction.ASC, "createTime");
        PageRequest pageRequest = new PageRequest(page, pageSize, sort);
        return tagRepository.findByNameLike(name, pageRequest);
    }


    public Tag findByName(String name){
        return tagRepository.findByName(name);
    }
}
