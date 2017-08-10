package cn.yangtengfei.api.filter;

import cn.yangtengfei.api.cacheService.authority.AuthorityCacheService;
import cn.yangtengfei.api.controller.base.BaseController;
import cn.yangtengfei.api.service.logicService.user.UserLogicService;
import cn.yangtengfei.api.util.cont.UserTokenConst;
import cn.yangtengfei.api.util.sdk.GeetestConfig;
import cn.yangtengfei.api.util.sdk.GeetestLib;
import cn.yangtengfei.model.user.User;
import cn.yangtengfei.regeditCodeUtil.ValidateCode;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;


@Service
@Slf4j
public class SystemFilter implements HandlerInterceptor {

    @Autowired
    private AuthorityCacheService authorityCacheService;

    @Autowired
    private UserLogicService userLogicService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if( handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            String cookieValue = "";
            String url = request.getRequestURI();
            String ip = request.getRemoteAddr();
            if(url.indexOf("/v1/api/captcha")!=-1) {
                /*response.setContentType("text/html; charset=utf-8");
                //禁止图像缓存。
                response.setHeader("Pragma", "no-cache");
                response.setHeader("Cache-Control", "no-cache,must-revalidate");
                response.setDateHeader("Expires", 0);
                response.setHeader("Transfer-Encoding","chunked");
                ValidateCode vCode = new ValidateCode(120,40,5,100);
                vCode.write(response.getOutputStream());*/
                GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(), GeetestConfig.isnewfailback());
                String resStr = "{}";
                //自定义userid
                String userid = "51jieguo";
                //进行验证预处理
                int gtServerStatus = gtSdk.preProcess(userid);
                //将服务器状态设置到session中
                request.getSession().setAttribute(gtSdk.gtServerStatusSessionKey, gtServerStatus);
                //将userid设置到session中
                request.getSession().setAttribute("userid", userid);

                resStr = gtSdk.getResponseStr();
                PrintWriter out = response.getWriter();
                out.println(resStr);
            }else if(url.indexOf("/v1/api/verify")!=-1) {
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
            }else if(url.indexOf("/v1/api/register")!=-1){
                log.info("用户注册");
                return userLogicService.checkRegisterCodeIsExist(request,response);
            }else if(url.indexOf("/v1/api/mail/sendMail")!=-1){
                log.info("IP : " + ip);
                return userLogicService.checkRegisterUserMailSend(request,response);
            }else if( url.indexOf("/v1/api/admin") != -1){
                Cookie[] cookies =request.getCookies();
                if(cookies!=null && cookies.length!=0){

                    for (Cookie cookie : cookies) {
                        if (UserTokenConst.COOKIE_USER_KEY.equals(cookie.getName())) {
                            cookieValue = cookie.getValue();
                        }
                    }
                    if (!StringUtils.isEmpty(cookieValue)) {
                        if (!authorityCacheService.chekAuthKeyIsExist(cookieValue)) {
                            response.setStatus(401);
                            return false;
                        } else {
                            //每次操作后增加session时间
                            authorityCacheService.addSessionTime(cookieValue, response);
                        }
                    }else {
                        //throw new CommonException(ErrorCode.Auth_Error_Code.NO_ACCESS,"NO_ACCESS");
                        response.setStatus(401);
                        return false;
                    }
                }else{
                    //throw new CommonException(ErrorCode.Auth_Error_Code.NO_ACCESS,"NO_ACCESS");
                    response.setStatus(401);
                    return false;
                }
            }
            User  user = authorityCacheService.getUer(cookieValue);
            if( handlerMethod.getBean() instanceof BaseController){
                BaseController baseAdminController = (BaseController) handlerMethod.getBean();
                baseAdminController.setUser(user);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
