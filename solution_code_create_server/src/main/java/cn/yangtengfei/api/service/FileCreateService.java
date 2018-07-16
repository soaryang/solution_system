package cn.yangtengfei.api.service;


import cn.yangtengfei.api.config.Config;
import cn.yangtengfei.api.util.DirectoryUtils;
import cn.yangtengfei.api.util.FileUtil;
import com.alibaba.fastjson.JSON;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import springfox.documentation.spring.web.json.Json;

import javax.annotation.PostConstruct;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class FileCreateService {


    @Autowired
    private Config config;
    public void createJson(){
        Map<String,Object> map = new HashMap<>();
        map.put("root","D:");
        map.put("projectName","tensflow"+"Project");
        System.out.println(JSON.toJSONString(map));
    }

    @PostConstruct
    public void createFile() throws IOException {
        String projectName = "tensflowProject";
        String companyName="yangtengfei";

        String basePackage = "com."+companyName;
        String rootPath = config.getRootPath(projectName);
        System.out.println(rootPath);

        boolean isHasChild = false;
        //创建根目录
        DirectoryUtils.createDirectory(rootPath);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("package",basePackage);
        map.put("projectName",projectName);
        String parentRootPath = config.getRootPath(projectName);

        String freemarkDirecotry = config.getfreemarkDirecotryh();
        if(isHasChild){
            //TODO:有子项目处理
            map.put("packageType","pom");
        }else{
            map.put("packageType","jar");
            //创建main目录
            String mainPath  = config.getMainPath(parentRootPath);
            DirectoryUtils.createDirectory(config.getJavePath(mainPath));
            String resourcePath =config.getResourcesPath(mainPath);
            DirectoryUtils.createDirectory(resourcePath);
            DirectoryUtils.createDirectory(config.getTestPath(mainPath));

            String bussinessPath =config.getBaseBusinessPath(mainPath,basePackage);
            //创建controller service
            String controllerPath = bussinessPath + File.separator + "controller";
            String servicePath = bussinessPath + File.separator + "service";
            DirectoryUtils.createDirectory(controllerPath,servicePath);
            //创建properties
            String propertiesPath=config.propertiesPath(resourcePath);
            FileUtil.createFileByFtl(propertiesPath,freemarkDirecotry,map);

        }
        String pomPath = config.pomPath(rootPath);
        FileUtil.createFileByFtl(pomPath,freemarkDirecotry,map);

        //String rootPath = "D:" + File.separator + projectName;
        //String mainPath = rootPath+File.separator+"src"+File.separator+"main";
        //String javaPath = mainPath+File.separator+"java";
        //String resourcesPath = mainPath+File.separator+"resources";
        /*String basePackage =javaPath +File.separator+ ("cn.yangtengfei."+projectName ).replace(".","/");
        String controllerPath = basePackage+File.separator+"controller";
        String servicePath = basePackage+File.separator+"service";
        DirectoryUtils.createDirectory(javaPath,resourcesPath,controllerPath,servicePath);




        String propertiesPath = resourcesPath+File.separator+"application.properties";
        String pomFile = rootPath + File.separator+"pom.xml";
        createFile(propertiesPath);
        createPom(rootPath);*/
    }



}
