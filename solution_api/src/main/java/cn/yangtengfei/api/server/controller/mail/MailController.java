package cn.yangtengfei.api.server.controller.mail;

import cn.yangtengfei.api.cacheService.user.RegisterCacheService;
import cn.yangtengfei.api.config.RestResult;
import cn.yangtengfei.api.util.RegisterUtil;
import freemarker.template.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/v1/api/mail")
@Slf4j
public class MailController {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Configuration freemarkerConfiguration;

    @Autowired
    private RegisterCacheService registerCacheService;


    @RequestMapping(value = "/sendMail", method = RequestMethod.GET)
    public RestResult sendMessage(HttpServletRequest request, String email){
        RestResult restResult = new RestResult();
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom("no_replay_51jieguo@126.com");
            helper.setTo(email);
            helper.setSubject("验证码");
            Map<String, Object> model = new HashMap<>();
            model.put("mail", email);
            String code = RegisterUtil.getRegisterCode();
            String ip = request.getRemoteAddr();
            registerCacheService.setMailRegisterCode(ip,code);
            model.put("code", code);
            String text = geFreeMarkerTemplateContent(model);
            helper.setText(text, true);
            mailSender.send(mimeMessage);
            restResult.setCode("200");
            return restResult;
        }catch (Exception e){
            log.error("发送邮件异常:{}",e);
            restResult.setCode("500");
            return restResult;
        }
    }

    public String geFreeMarkerTemplateContent(Map<String, Object> model)
    {
        StringBuffer content = new StringBuffer();
        try
        {
            content.append(FreeMarkerTemplateUtils.processTemplateIntoString(
                    freemarkerConfiguration.getTemplate("mail.ftl"), model));
            return content.toString();
        }
        catch (Exception e)
        {
            log.error("Exception occured while processing fmtemplate:" + e.getMessage(), e);
        }
        return "";
    }
}