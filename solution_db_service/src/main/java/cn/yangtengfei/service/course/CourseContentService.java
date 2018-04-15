package cn.yangtengfei.service.course;

import cn.yangtengfei.model.course.Course;
import cn.yangtengfei.model.course.CourseContent;
import cn.yangtengfei.repository.course.CourseContentRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseContentService {

    @Autowired
    private CourseContentRespository courseContentRespository;

    public void save(CourseContent courseContent){
        courseContentRespository.save(courseContent);
    }

    public void CourseContent(String id){
        courseContentRespository.findOne(id);
    }

    public List<CourseContent> findCourseContentByCourseId(String courseId){
        return courseContentRespository.findByCourseId(courseId);
    }

    public Page<CourseContent> findByDeleteFlgOrderByUpdateTimeDesc(Integer deleteFlg, int page, int pageSize){
        PageRequest pageRequest = new PageRequest(page, pageSize);
        return courseContentRespository.findByDeleteFlgOrderByUpdateTimeDesc(deleteFlg,pageRequest);
    }


}
