package cn.yangtengfei.api.server.controller.user;


import cn.yangtengfei.api.config.PageResultModel;
import cn.yangtengfei.api.config.RestResult;
import cn.yangtengfei.api.service.dataService.user.ApiRoleService;
import cn.yangtengfei.api.server.view.user.RoleView;
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
    public RestResult save(@ModelAttribute RoleView roleView){
        RestResult restResult = new RestResult();
        restResult.setCode("200");
        apiRoleService.saveRole(roleView);
        return restResult;
    }




    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public RestResult findAll(){
        RestResult restResult = new RestResult();
        restResult.setCode("200");
        restResult.setData(apiRoleService.findAll(0));
        return restResult;
    }


    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public PageResultModel findAll(Integer pageNumber , Integer pageSize){
        logger.info("pageNumber:{},pageSize:{}",pageNumber,pageSize);
        return apiRoleService.findAll(0,pageNumber-1,pageSize);
    }

}
