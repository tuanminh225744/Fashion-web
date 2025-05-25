package ktpm.projectsoftware.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ktpm.projectsoftware.repository.ThongTinCuaHangRepository;

@Service
public class CuaHangService {
    @Autowired
    ThongTinCuaHangRepository ttchRepo;
    @Autowired
    PasswordEncoder pe;
    public void capNhatThongTin(String thong_tin) {
        ttchRepo.capNhatThongTin(thong_tin);
    }

    public void capNhatChinhSach(String thong_tin) {
        ttchRepo.capNhatChinhSach(thong_tin);
    }
    public String getThongTin(){
        return ttchRepo.findById(1).get().getThongTin();
    }
    public String getChinhSach(){
          return ttchRepo.findById(1).get().getChinhSach();
    }

}
