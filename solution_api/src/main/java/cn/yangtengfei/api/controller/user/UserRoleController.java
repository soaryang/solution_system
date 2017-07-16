package cn.yangtengfei.api.controller.user;


import cn.yangtengfei.api.config.Result;
import cn.yangtengfei.api.service.user.ApiUserService;
import cn.yangtengfei.api.view.user.RoleView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/api/admin/userRole")
public class UserRoleController {

    @Autowired
    private ApiUserService apiUserService;

    @RequestMapping(value = "/setRole", method = RequestMethod.POST)
    public Result save(String userId, String roleId){
        Result result = new Result();
        result.setCode("200");
        apiUserService.setRole(userId,roleId);
        return result;
    }

}
