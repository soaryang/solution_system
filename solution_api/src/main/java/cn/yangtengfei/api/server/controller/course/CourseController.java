package cn.yangtengfei.api.server.controller.course;


import cn.yangtengfei.api.config.PageResultModel;
import cn.yangtengfei.api.config.Result;
import cn.yangtengfei.api.server.controller.base.BaseController;
import cn.yangtengfei.model.course.Course;
import cn.yangtengfei.model.question.Question;
import cn.yangtengfei.model.question.Tag;
import cn.yangtengfei.service.course.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/v1/api/admin/course")
public class CourseController extends BaseController {

    @Autowired
    private CourseService courseService;

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public PageResultModel findAll(HttpServletRequest request,Integer pageNumber , Integer pageSize){
        String name = request.getParameter("name");
        int start = pageNumber-1;
        PageResultModel pageResultModel = new PageResultModel();
        Page<Course> coursesPage = courseService.findByDeleteFlgOrderByUpdateTimeDesc(0,start,pageSize);
        pageResultModel.setTotal(coursesPage.getTotalElements());
        pageResultModel.setRows(coursesPage.getContent());
        return  pageResultModel;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result findAll(HttpServletRequest request){
        String name = request.getParameter("courseName");
        Course course = new Course();
        course.setName(name);
        courseService.save(course);
        Result result = new Result();
        result.setCode("200");
        return result;
    }
}
