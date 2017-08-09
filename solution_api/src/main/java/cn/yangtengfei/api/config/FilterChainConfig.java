package cn.yangtengfei.api.config;

import cn.yangtengfei.api.filter.SystemFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class FilterChainConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private SystemFilter authorityFilter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorityFilter).addPathPatterns("/**");
        //registry.addInterceptor(new UserNamePasswordFilter()).addPathPatterns("/v1/api/login");
        super.addInterceptors(registry);
    }

}