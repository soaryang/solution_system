package cn.yangtengfei.api.config;


import cn.yangtengfei.api.service.FileCreateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class Config {



    @Value("${freemarkDirecotry:}")
    public String freemarkDirecotry;


    public String getExceDirecotryh(){
        if(StringUtils.isBlank(freemarkDirecotry)){
            return this.getClass().getResource("/templates").getPath()+"/excel";
        }
        return freemarkDirecotry;
    }


    public String getfreemarkDirecotryh(){
        if(StringUtils.isBlank(freemarkDirecotry)){
            return this.getClass().getResource("/templates").getPath()+"/freemark";
        }
        return freemarkDirecotry;
    }
    /**
     * 项目路径
     *
     * @param projectName
     * @return
     */
    public String getRootPath(String projectName) {
        return this.getClass().getResource("/").getPath()+"temp"+File.separator  + projectName;
    }

    public String getMainPath(String rootPath) {
        return rootPath + File.separator + "src" + File.separator + "main";
    }

    public String getJavePath(String mainPath) {
        return mainPath + File.separator + "java";
    }

    public String getResourcesPath(String mainPath) {
        return mainPath + File.separator + "resources";
    }

    public String getTestPath(String mainPath) {
        return mainPath + File.separator + "test";
    }

    public String pomPath(String path) {
        return path + File.separator + "pom.xml";
    }

    public String propertiesPath(String path) {
        return path + File.separator + "application.properties";
    }

    public String getBaseBusinessPath(String mainPath,String basePackage){
        return getJavePath(mainPath)+ File.separator+ basePackage.replace(".","/");
    }


}
