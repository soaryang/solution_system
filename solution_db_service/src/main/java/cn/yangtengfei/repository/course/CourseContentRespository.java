package cn.yangtengfei.repository.course;


import cn.yangtengfei.model.course.Course;
import cn.yangtengfei.model.course.CourseContent;
import cn.yangtengfei.model.question.QuestionCount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;

@EnableMongoRepositories(mongoTemplateRef = "courseMongoProperties")
public interface CourseContentRespository  extends MongoRepository<CourseContent,String> {

    List<CourseContent> findByCourseId(String courseId);

    Page<CourseContent> findByCourseIdAndDeleteFlgOrderByUpdateTimeDesc(String courseId,Integer deleteFlg, Pageable pageable);


}
