package cn.yangtengfei.api.service;


import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import springfox.documentation.spring.web.json.Json;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class FileCreateService {


    private String jsonStr = "{\"123132\":\"123132123\"}";



    public void createJson(){
        Map<String,Object> map = new HashMap<>();
        map.put("root","D:");
        map.put("projectName","tensflow"+"Project");
        System.out.println(JSON.toJSONString(map));
    }

    @PostConstruct
    public void createFile() throws IOException {
        String projectName = "tensflowProject";
        String rootPath = "D:" + File.separator + projectName;
        String mainPath = rootPath+File.separator+"src"+File.separator+"main";
        String javaPath = mainPath+File.separator+"java";
        String resourcesPath = mainPath+File.separator+"resources";
        String propertiesPath = resourcesPath+File.separator+"application.properties";

        String basePackage =javaPath +File.separator+ ("cn.yangtengfei."+projectName ).replace(".","/");
        
        String controllerPath = basePackage+File.separator+"controller";
        String servicePath = basePackage+File.separator+"service";

        String pomFile = rootPath + File.separator+"pom.xml";
        createDirectory(javaPath,resourcesPath,controllerPath,servicePath);

        createFile(propertiesPath,pomFile);
    }

    public void createDirectory(String ...paths){
        for(String path:paths){
            File file = new File(path);
            file.mkdirs();
        }
    }

    public void createFile(String... paths)  {

        for(String path:paths){
            File f = new File(path);
            if(!f.exists()){
                try {
                    f.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
