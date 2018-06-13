package cn.yangtengfei.model.course;

import cn.yangtengfei.baseModel.BaseModel;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "courseContent")
public class CourseContent extends BaseModel {

    private String id;

    private String courseId;

    private String courseContentName;

    private String markDownContent;

}
