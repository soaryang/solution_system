package cn.yangtengfei.repository.course;

import cn.yangtengfei.model.course.Course;
import cn.yangtengfei.model.course.CourseContent;
import cn.yangtengfei.model.question.Solution;
import cn.yangtengfei.model.question.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;

@EnableMongoRepositories(mongoTemplateRef = "courseMongoProperties")
public interface CourseRespository extends MongoRepository<Course,String> {

    Page<Tag> findByDeleteFlgOrderByUpdateTimeDesc(Integer deleteFlg, Pageable pageable);
}
