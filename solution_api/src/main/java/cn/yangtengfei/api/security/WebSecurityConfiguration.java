package cn.yangtengfei.api.security;

import cn.yangtengfei.api.security.filter.TokenProcessingFilter;
import cn.yangtengfei.api.security.login.RestAuthenticationFailureHandler;
import cn.yangtengfei.api.security.login.RestAuthenticationSuccessHandler;
import cn.yangtengfei.api.util.MD5Util;
import cn.yangtengfei.api.util.UserTokenConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private RestAuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    private RestAuthenticationSuccessHandler successHandler;


    @Autowired
    private AuthenticationProviderCustom authenticationProviderCustom;


    @Override  
    protected void configure(HttpSecurity http) throws Exception {
  
        http.csrf().disable();// csrf:Cross-site request forgery跨站请求伪造

        
        http.authenticationProvider(authenticationProviderCustom);
        //http.authenticationProvider(authenticationManager().authenticate())

        http.formLogin().loginProcessingUrl("/v1/api/login").failureHandler(authenticationFailureHandler).successHandler(successHandler).permitAll();
        http.logout().logoutUrl("/v1/api/LogOut").permitAll();
        http.authorizeRequests().antMatchers("/v1/api/user/**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/v1/api/admin/question/**","/v1/api/admin/tag/**","/v1/api/admin/solution/**").hasAnyAuthority("ADMIN","USER");

        //http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //http.addFilterBefore(tokenProcessingFilter,TokenProcessingFilter.class);
        //http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class)
        //http.authorizeRequests().antMatchers("/user/*").hasAnyAuthority("USER");//拦截指定url,放行指定角色
        //http.rememberMe().rememberMeCookieName(UserTokenConst.COOKIE_AUTH_KEY).tokenValiditySeconds(60*60*30);
        //http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class);
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new PasswordEncoder(){

            @Override
            public String encode(CharSequence rawPassword) {
                return MD5Util.encode((String)rawPassword);
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return encodedPassword.equals(MD5Util.encode((String)rawPassword));
            }}); //user Details Service验证
    }
}