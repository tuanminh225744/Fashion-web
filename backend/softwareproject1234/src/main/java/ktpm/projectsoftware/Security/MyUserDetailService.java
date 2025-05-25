package ktpm.projectsoftware.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ktpm.projectsoftware.entity.NguoiDung;
import ktpm.projectsoftware.repository.NguoiDungRepository;


@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private NguoiDungRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String ten)
            throws UsernameNotFoundException {
        NguoiDung us = userRepository.findByTenAndDaDangKy(ten,true);

        if (us == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
        return new MyUserDetails(us);
    }

}