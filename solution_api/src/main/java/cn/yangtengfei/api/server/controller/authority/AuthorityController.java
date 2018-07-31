package cn.yangtengfei.api.server.controller.authority;


import cn.yangtengfei.api.config.RestResult;
import cn.yangtengfei.api.server.controller.base.BaseController;
import cn.yangtengfei.api.exception.CommonException;
import cn.yangtengfei.api.service.logicService.authority.AuthorityService;
import cn.yangtengfei.api.util.ErrorCode;
import cn.yangtengfei.api.util.RandomCodeUtil;
import cn.yangtengfei.api.util.sdk.GeetestConfig;
import cn.yangtengfei.api.util.sdk.GeetestLib;
import cn.yangtengfei.api.server.view.user.UserView;
import cn.yangtengfei.model.user.User;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@RestController
@RequestMapping(value = "/v1/api")
public class AuthorityController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(AuthorityController.class);

    @Autowired
    private AuthorityService authorityService;

    @RequestMapping(value = "/captcha", method = {RequestMethod.GET, RequestMethod.POST})
    public void captcha(HttpServletRequest request,HttpServletResponse response) throws Exception {
        GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(), GeetestConfig.isnewfailback());
        String resStr = "{}";
        //自定义userid
        String userid = RandomCodeUtil.getCode();
        //进行验证预处理
        int gtServerStatus = gtSdk.preProcess(userid);
        //将服务器状态设置到session中
        request.getSession().setAttribute(gtSdk.gtServerStatusSessionKey, gtServerStatus);
        //将userid设置到session中
        request.getSession().setAttribute("userid", userid);
        resStr = gtSdk.getResponseStr();
        PrintWriter out = response.getWriter();
        out.println(resStr);
    }
    @RequestMapping(value = "/register", method = {RequestMethod.GET, RequestMethod.POST})
    public void register(@ModelAttribute UserView userView,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*RestResult result = new RestResult();
        apiUserService.saveUserByUserView(userView);
        result.setCode("200");
        return result;*/
        GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(), GeetestConfig.isnewfailback());
        String challenge = request.getParameter(GeetestLib.fn_geetest_challenge);
        String validate = request.getParameter(GeetestLib.fn_geetest_validate);
        String seccode = request.getParameter(GeetestLib.fn_geetest_seccode);
        //从session中获取gt-server状态
        int gt_server_status_code = (Integer) request.getSession().getAttribute(gtSdk.gtServerStatusSessionKey);
        //从session中获取userid
        String userid = (String)request.getSession().getAttribute("userid");
        int gtResult = 0;
        if (gt_server_status_code == 1) {
            //gt-server正常，向gt-server进行二次验证
            gtResult = gtSdk.enhencedValidateRequest(challenge, validate, seccode, userid);
            System.out.println(gtResult);
        } else {
            // gt-server非正常情况下，进行failback模式验证
            System.out.println("failback:use your own server captcha validate");
            gtResult = gtSdk.failbackValidateRequest(challenge, validate, seccode);
            System.out.println(gtResult);
        }
        if (gtResult == 1) {
            // 验证成功
            PrintWriter out = response.getWriter();
            JSONObject data = new JSONObject();
            try {
                data.put("status", "success");
                data.put("version", gtSdk.getVersionInfo());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            out.println(data.toString());
        }
        else {
            // 验证失败
            JSONObject data = new JSONObject();
            try {
                data.put("status", "fail");
                data.put("version", gtSdk.getVersionInfo());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            PrintWriter out = response.getWriter();
            out.println(data.toString());
        }
    }

    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public RestResult findAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            throw new CommonException(ErrorCode.Auth_Error_Code.USERNAME_OR_PASSWORD_IS_NULL, "USERNAME OR  PASSWORD IS NULL");
        }
        RestResult restResult = new RestResult();
        User user = authorityService.userLogin(userName, password,response);
        if(user!=null){
            restResult.setCode("200");
        }else{
            restResult.setCode("500");
        }
        return restResult;
    }
}
