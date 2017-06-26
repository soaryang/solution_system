package cn.yangtengfei.api.security.filter;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TokenProcessingFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = this.getAsHttpRequest(request);
        String authToken = this.extractAuthTokenFromRequest(httpRequest);
        /*String userName = TokenUtils.getUserNameFromToken(authToken);
        if (userName != null) {
            //UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
            UserDetails userDetails = fakeUserDetails();
            if (TokenUtils.validateToken(authToken, userDetails)) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            }
        }*/
        chain.doFilter(request, response);
    }

    private HttpServletRequest getAsHttpRequest(ServletRequest request){
        if (!(request instanceof HttpServletRequest)) {
            throw new RuntimeException("Expecting an HTTP request");
        }
        return (HttpServletRequest) request;
    }


    private String extractAuthTokenFromRequest(HttpServletRequest httpRequest) {
        /* Get token from header */
        String authToken = httpRequest.getHeader("x-auth-token");
        /* If token not found get it from request parameter */
        if (authToken == null) {
            authToken = httpRequest.getParameter("token");
        }
        return authToken;
    }

    private UserDetails fakeUserDetails(){
        UsernamePasswordAuthenticationToken authenticationToken = new
                UsernamePasswordAuthenticationToken("user","password");

        List<SimpleGrantedAuthority> auth= new ArrayList<>();
        auth.add(new SimpleGrantedAuthority("USER"));
        //return  new User("user","password",auth);
        return new org.springframework.security.core.userdetails.User("user", "a3caed36f0fe5a01e5f144db8927235e", auth);
    }
}