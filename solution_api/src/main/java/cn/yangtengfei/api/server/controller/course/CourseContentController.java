package cn.yangtengfei.api.server.controller.course;

import cn.yangtengfei.api.config.PageResultModel;
import cn.yangtengfei.api.config.Result;
import cn.yangtengfei.api.server.controller.base.BaseController;
import cn.yangtengfei.model.course.Course;
import cn.yangtengfei.model.course.CourseContent;
import cn.yangtengfei.service.course.CourseContentService;
import cn.yangtengfei.service.course.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping(value = "/v1/api/admin/courseContent")
public class CourseContentController extends BaseController {

    @Autowired
    private CourseContentService courseContentService;
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public PageResultModel findAll(HttpServletRequest request, Integer pageNumber , Integer pageSize){
        String courseId = request.getParameter("courseId");
        int start = pageNumber-1;
        PageResultModel pageResultModel = new PageResultModel();
        Page<CourseContent> coursesPage = courseContentService.findByDeleteFlgOrderByUpdateTimeDesc(0,start,pageSize);
        pageResultModel.setTotal(coursesPage.getTotalElements());
        pageResultModel.setRows(coursesPage.getContent());
        return  pageResultModel;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result findAll(HttpServletRequest request){
        String courseId = request.getParameter("courseId");
        String courseContentName = request.getParameter("courseContentName");
        String markDownContent = request.getParameter("markDownContent");

        CourseContent courseContent = new CourseContent();
        courseContent.setCourseId(courseId);
        courseContent.setCourseContentName(courseContentName);
        courseContent.setMarkDownContent(markDownContent);

        courseContentService.save(courseContent);
        Result result = new Result();
        result.setCode("200");
        return result;
    }

}
