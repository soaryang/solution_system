package cn.yangtengfei.api.security.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;


@Component("myAuthenticationManager")
public class MyAuthenticationManager implements AuthenticationManager {

    @Autowired
    private UserDetailsService userDetailsService;


    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        if (auth.getName() != null && auth.getCredentials() != null) {
            User user = (User) userDetailsService.loadUserByUsername(auth.getName());
            return new UsernamePasswordAuthenticationToken(user,
                    null, user.getAuthorities());
        }
        throw new BadCredentialsException("Bad Credentials");
    }
}
