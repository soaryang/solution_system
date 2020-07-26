package cn.yangtengfei.api.util.file;

import freemarker.template.Configuration;
import freemarker.template.Template;
import sun.rmi.runtime.Log;

import java.io.*;
import java.util.Map;
import java.util.logging.Logger;

public class FreemarkerUtil {



    public static void createFreemarker(Map<String, Object> dataMap, String freemarkerPath, String freemarkerName, String aimFilePath) {
        Configuration configuration = new Configuration();
        Writer out = null;
        try {
            // step2 获取模版路径
            configuration.setDirectoryForTemplateLoading(new File(freemarkerPath));
            // step4 加载模版文件
            Template template = configuration.getTemplate(freemarkerName);
            // step5 生成数据
            File docFile = new File(aimFilePath);
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile)));
            // step6 输出文件
            template.process(dataMap, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != out) {
                    out.flush();
                    out.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}
