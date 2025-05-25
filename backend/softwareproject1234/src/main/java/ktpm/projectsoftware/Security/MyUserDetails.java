package ktpm.projectsoftware.Security;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import ktpm.projectsoftware.entity.NguoiDung;


@Service
public class MyUserDetails implements UserDetails {
    private NguoiDung us;
    public MyUserDetails(){
    }
    public MyUserDetails(NguoiDung us) {
        this.us = us;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(us.getVaiTro());
        return Arrays.asList(authority);
        
    }

    @Override
    public String getPassword() {
        return us.getMatKhau();
    }

    @Override
    public String getUsername() {
        return us.getTen();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}