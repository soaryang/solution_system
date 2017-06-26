package cn.yangtengfei.api.service.user;


import cn.yangtengfei.api.view.user.SecurityUser;
import cn.yangtengfei.model.user.User;
import cn.yangtengfei.model.user.UserRole;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserDetailsServiceImple implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
             /*final User cUser = userService.getByUsername(username);
	        if (cUser == null) {
	            throw new UsernameNotFoundException(username + " cannot be found");
	        }*/

        User user = new User();
        user.setName(username);
        List<UserRole> userRoleList = new ArrayList<>();
        UserRole userRole = new UserRole();
        userRole.setName("USER");
        userRoleList.add(userRole);
        //org.springframework.security.core.userdetails.User userDetails = new SecurityUser(user,userRoleList);
        //final Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        //authorities.add(new SimpleGrantedAuthority("USER"));
        final Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER"));
        return new org.springframework.security.core.userdetails.User("user", "a3caed36f0fe5a01e5f144db8927235e", authorities);
        //return  userDetails;
    }

}
