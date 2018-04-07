package cn.yangtengfei.service.course;


import cn.yangtengfei.model.course.Course;
import cn.yangtengfei.model.question.Tag;
import cn.yangtengfei.repository.course.CourseRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

    @Autowired
    private CourseRespository courseRespository;

    public void save(Course course){
        courseRespository.save(course);
    }

    public Page<Tag> findByDeleteFlgOrderByUpdateTimeDesc(Integer deleteFlg,int page, int pageSize){
        PageRequest pageRequest = new PageRequest(page, pageSize);
        return courseRespository.findByDeleteFlgOrderByUpdateTimeDesc(deleteFlg,pageRequest);
    }

    public long count(){
        return courseRespository.count();
    }


}
