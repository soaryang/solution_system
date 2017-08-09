package cn.yangtengfei.api.cacheService.user;


import cn.yangtengfei.cacheKey.user.RegisterCacheKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RegisterCacheService {


    @Cacheable(value= RegisterCacheKey.TEMP_KEY,key = "'"+ RegisterCacheKey.TEMP_IP_MAIL_REQUEST_COUNT_KEY+"'+#ip")
    public int  findIpMailCount(String ip){

        return  0;
    }

    @Caching(put = {
            @CachePut(value= RegisterCacheKey.TEMP_KEY,key = "'"+ RegisterCacheKey.TEMP_IP_MAIL_REQUEST_COUNT_KEY+"'+#ip",cacheManager = "mailSendCountCache")
    })
    public int  setIpMailCount(String ip,int count){
        return  count;
    }


    @Cacheable(value= RegisterCacheKey.TEMP_KEY,key = "'"+ RegisterCacheKey.TEMP_IP_MAIL_REQUEST_CONTENT_KEY+"'+#ip")
    public String  findMailRegisterCode(String ip){
        return "";
    }

    @Caching(put = {
            @CachePut(value= RegisterCacheKey.TEMP_KEY,key = "'"+ RegisterCacheKey.TEMP_IP_MAIL_REQUEST_CONTENT_KEY+"'+#ip")
    })
    public String setMailRegisterCode(String ip,String  register){
        return  register;
    }



}
