package cn.yangtengfei.api.server.controller.user;


import cn.yangtengfei.api.config.RestResult;
import cn.yangtengfei.api.service.dataService.user.ApiUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/api/admin/userRole")
public class UserRoleController {

    @Autowired
    private ApiUserService apiUserService;

    @RequestMapping(value = "/setRole", method = RequestMethod.GET)
    public RestResult save(String userId, String roleId){
        RestResult restResult = new RestResult();
        restResult.setCode("200");
        apiUserService.setRole(userId,roleId);
        return restResult;
    }

}
