package cn.yangtengfei.createProject.api.service;


import cn.yangtengfei.createProject.api.util.Commutil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class ModelService {

    public Map<String,Object> moduleMap(String moduleJson){


        Map<String,Object> dataMap = new HashMap<>();
        Map maps = (Map)JSON.parse(moduleJson);

        //项目名称
        String projectName = Commutil.converObjectToString(maps.get("name"));
        String company = Commutil.converObjectToString(maps.get("company"));
        String domain = Commutil.converObjectToString(maps.get("domain"));
        setDataToDataMap("name",projectName,dataMap);
        setDataToDataMap("company",company,dataMap);
        setDataToDataMap("domain",domain,dataMap);

        return dataMap;
    }


    public void setDataToDataMap(String key,Object object,Map<String,Object> dataMap){
        dataMap.put(key,object);
    }

}
