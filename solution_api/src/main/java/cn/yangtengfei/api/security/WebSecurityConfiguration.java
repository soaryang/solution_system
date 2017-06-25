package cn.yangtengfei.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private RestAuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    private RestAuthenticationSuccessHandler successHandler;

    @Override  
    protected void configure(HttpSecurity http) throws Exception {
  
        http.csrf().disable();// csrf:Cross-site request forgery跨站请求伪造  
          
        http.formLogin() // 登陆表单  
                .failureHandler(authenticationFailureHandler) // failure handler  
                .successHandler(successHandler) // success handler  
                .loginProcessingUrl("/v1/api/login")// default is /login with an HTTP// post
                .permitAll(); // premit all authority  
        http.authorizeRequests().antMatchers("/user/*").hasAnyAuthority("USER");//拦截指定url,放行指定角色  
    }  
  
    @Autowired  
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //auth.userDetailsService(userDetailsService);
        auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");//创建一个默认的用户存储在内存中
    }  
}