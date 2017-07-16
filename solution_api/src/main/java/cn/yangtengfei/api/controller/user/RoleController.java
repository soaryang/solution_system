package cn.yangtengfei.api.controller.user;


import cn.yangtengfei.api.config.PageResultModel;
import cn.yangtengfei.api.config.Result;
import cn.yangtengfei.api.service.user.ApiRoleService;
import cn.yangtengfei.api.view.user.RoleView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/api/admin/role")
public class RoleController {

    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private ApiRoleService apiRoleService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(@ModelAttribute RoleView roleView){
        Result result = new Result();
        result.setCode("200");
        apiRoleService.saveRole(roleView);
        return result;
    }


    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public PageResultModel findAll(Integer pageNumber , Integer pageSize){
        logger.info("pageNumber:{},pageSize:{}",pageNumber,pageSize);
        return apiRoleService.findAll(0,pageNumber-1,pageSize);
    }

}
