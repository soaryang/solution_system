package cn.yangtengfei.api.config;

import cn.yangtengfei.api.filter.AuthorityFilter;
import cn.yangtengfei.api.filter.UserSessionFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class FilterChainConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthorityFilter()).addPathPatterns("/**");
        //registry.addInterceptor(new UserNamePasswordFilter()).addPathPatterns("/v1/api/login");
        super.addInterceptors(registry);
    }

}