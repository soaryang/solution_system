package cn.yangtengfei.api.security;

import cn.yangtengfei.api.security.filter.BeforeLoginFilter;
import cn.yangtengfei.api.security.filter.MyUserNamePasswordAuthenticationFilter;
import cn.yangtengfei.api.security.login.RestAuthenticationFailureHandler;
import cn.yangtengfei.api.security.login.RestAuthenticationSuccessHandler;
import cn.yangtengfei.api.security.logout.RestLogoutSuccessHandler;
import cn.yangtengfei.api.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
//覆盖默认的spring security提供的配置
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private RestAuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    private RestAuthenticationSuccessHandler successHandler;
    @Autowired
    private AuthenticationProviderCustom authenticationProviderCustom;

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private RestLogoutSuccessHandler restLogoutSuccessHandler;



    @Autowired
    private AuthenticationManager authenticationManager;

    @Override  
    protected void configure(HttpSecurity http) throws Exception {

        //http.authenticationProvider(authenticationProviderCustom);
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and().formLogin().loginProcessingUrl("/v1/api/login")
                .failureHandler(authenticationFailureHandler).successHandler(successHandler).permitAll()
                .and()
                .logout().logoutSuccessHandler(restLogoutSuccessHandler)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                .rememberMe().tokenValiditySeconds(20).alwaysRemember(true)
                .and()
                .csrf().disable();
        http.addFilterBefore(new BeforeLoginFilter(),UsernamePasswordAuthenticationFilter.class);

        //MyUserNamePasswordAuthenticationFilter myUserNamePasswordAuthenticationFilter = new MyUserNamePasswordAuthenticationFilter();
        //myUserNamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManager);
        //myUserNamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(successHandler);
        //http.addFilterAt(myUserNamePasswordAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);
        //http.authenticationProvider(authenticationManager().authenticate())

        //http.formLogin().loginProcessingUrl("/v1/api/login").failureHandler(authenticationFailureHandler).successHandler(successHandler).permitAll();
        //http.logout().logoutUrl("/v1/api/LogOut").permitAll();
        //http.authorizeRequests().antMatchers("/v1/api/user/**").hasAnyAuthority("ADMIN");
        //http.authorizeRequests().antMatchers("/v1/api/admin/question/**","/v1/api/admin/tag/**","/v1/api/admin/solution/**").hasAnyAuthority("ADMIN","USER");
        //http.rememberMe().tokenValiditySeconds(60*60*24*7);


        //.rememberMe()
                //.tokenValiditySeconds(10).rememberMeCookieName("auth_key");
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
            }});

    }

}

