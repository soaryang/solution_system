package cn.yangtengfei.api.view.user;

import cn.yangtengfei.model.user.User;
import cn.yangtengfei.model.user.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class SecurityUser extends User implements UserDetails {
    private static final long serialVersionUID = 1L;

    private List<UserRole> userRoleList;

    public SecurityUser(User user, List<UserRole> userRoleList) {
        this.setId(user.getId());
        this.setName(user.getName());
        this.setEmail(user.getEmail());
        this.setPassword(user.getPassword());
        this.userRoleList =userRoleList;
    }
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        if(userRoleList!=null && userRoleList.size()>0){
            for (UserRole userRole : userRoleList) {
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userRole.getName());
                authorities.add(authority);
            }
        }
        return authorities;
    }
    @Override
    public String getPassword() {return super.getPassword();}
    @Override
    public String getUsername() {return super.getEmail();}
    @Override
    public boolean isAccountNonExpired() {return true;}
    @Override
    public boolean isAccountNonLocked() {return true;}
    @Override
    public boolean isCredentialsNonExpired() {return true;}
    @Override
    public boolean isEnabled() {return true;}


}