package cn.yangtengfei.api.util.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;
import org.springframework.util.ResourceUtils;

public class CreateHtmlByFreemarker {
    private static final String TEMPLATE_PATH = "templates";
    private static final String CLASS_PATH = "D:/";

    public static void createFreemarker() {
        Configuration configuration = new Configuration();
        Writer out = null;
        try {
            // step2 获取模版路径
            configuration.setDirectoryForTemplateLoading(new File( ResourceUtils.getURL("classpath:").getPath()+File.separator+TEMPLATE_PATH));
            // step3 创建数据模型
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("username", "zhangsan");
            dataMap.put("password", "123456");
            dataMap.put("age", "18");
            dataMap.put("address", "test");
            // step4 加载模版文件
            Template template = configuration.getTemplate("user.ftl");
            // step5 生成数据
            File docFile = new File(CLASS_PATH + "/" + "user.html");
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile)));
            // step6 输出文件
            template.process(dataMap, out);
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^user.ftl 文件创建成功 !");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != out) {
                    out.flush();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

}