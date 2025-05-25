package ktpm.projectsoftware.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.twilio.rest.api.v2010.Account;

import java.util.regex.Pattern;

import ktpm.projectsoftware.Exception.MaSaiHoacHetHan;
import ktpm.projectsoftware.Exception.NguoiDungDaDangKy;
import ktpm.projectsoftware.Security.JwtService;
import ktpm.projectsoftware.Security.MyUserDetails;
import ktpm.projectsoftware.entity.NguoiDung;
import ktpm.projectsoftware.entity.SanPham;
import ktpm.projectsoftware.repository.NguoiDungRepository;
import ktpm.projectsoftware.repository.SanPhamRepository;

@Service
public class DichVuNguoiDung {
    @Autowired
    NguoiDungRepository repo;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    EmailSender sender;
    @Autowired
    SanPhamRepository sprepo;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    public NguoiDung timNguoiDungHienTai() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails u = (UserDetails) principal;
        String ten = u.getUsername();
        NguoiDung nd = repo.findByten(ten);
        return nd;
    }
    public String taoMa() {
        Random random = new Random();
        int number = random.nextInt(100000); // Generates 0 to 99999
        return String.format("%05d", number); // Ensures 5-digit format with leading zeros
    }

    public static boolean isValidEmail(String email) {
        String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (email == null || email.isEmpty()) {
            return false; // Empty or null emails are invalid
        }

        return Pattern.matches(EMAIL_REGEX, email);
    }

    public NguoiDung KhachHangChuaDangKy(NguoiDung nd) throws Exception {
        NguoiDung ndht = repo.findByten(nd.getTen());
        if (ndht == null) {
            String ma = taoMa();
            nd.setDaDangKy(false);
            nd.setMaXacNhan(ma);
            nd.setMatKhau(encoder.encode(nd.getMatKhau()));
            nd.setVaiTro("KhachHang");
            nd.setThoiHan(LocalDateTime.now().plusHours(1));
            sender.sendEmail(ma, nd.getTen());
            return repo.save(nd);

        } else if (ndht.isDaDangKy() == false) {
            String ma = taoMa();
            ndht.setMatKhau(encoder.encode(nd.getMatKhau()));
            ndht.setMaXacNhan(ma);
            ndht.setThoiHan(LocalDateTime.now().plusHours(1));
            if (isValidEmail(ndht.getTen()))
                sender.sendEmail(ma, ndht.getTen());
            return repo.save(ndht);
        } else
            throw new NguoiDungDaDangKy("Tai khoan da co nguoi dang ky");
    }

    public NguoiDung NguoiDungDangKyThanhCong(String ten, String mxn) throws Exception {
        NguoiDung nd = repo.findByTenAndMaXacNhan(ten, mxn);
        if (nd != null && nd.isDaDangKy() == false && LocalDateTime.now().isBefore(nd.getThoiHan())) {
            nd.setDaDangKy(true);
            return repo.save(nd);
        }
        throw new MaSaiHoacHetHan("Tai khoan da dang ky,ma sai hoac het han");
    }
    public void DatLaiMatKhau(String email){
        NguoiDung ndht = repo.findByten(email);
        if(ndht==null||ndht.isDaDangKy()==false)
            throw new RuntimeException("Tài khoản chưa tồn tại");
        String ma=taoMa();
        ndht.setMaXacNhan(ma);
        ndht.setThoiHan(LocalDateTime.now().plusHours(1));
        repo.save(ndht);
        sender.sendEmail(ma, ndht.getTen());
    }
    public void DatLaiMatKhauThanhCong(String email,String mxn,String new_pass){
        NguoiDung nd = repo.findByTenAndMaXacNhan(email, mxn);
        if (nd != null && nd.isDaDangKy() == true && LocalDateTime.now().isBefore(nd.getThoiHan())){
           nd.setMatKhau(encoder.encode(new_pass));
           repo.save(nd);
        }
        else
           throw new RuntimeException("Reset mật khẩu thất bại,vui lòng thử lại");
    }

    public NguoiDung themSanPhamVaoGioHang(int sanphamid) {
        NguoiDung nd = timNguoiDungHienTai();
        SanPham sp = sprepo.findById(sanphamid);
        Collection<SanPham> l = nd.getSanpham();
        l.add(sp);
        nd.setSanpham(l);
        return repo.save(nd);

    }
    public void xoaSanPhamKhoiGioHang(int san_phamid){
        NguoiDung nd=timNguoiDungHienTai();
        repo.xoaKhoiGioHang(nd.getID(), san_phamid);
    }
    public ArrayList<SanPham> timKiemGioHang(){
        NguoiDung nd=timNguoiDungHienTai();
        return repo.timKiemGioHang(nd.getID());
    }
    public void update(NguoiDung nd){
        NguoiDung ndht=timNguoiDungHienTai();
        ndht.setGioiTinh(nd.getGioiTinh());
        ndht.setDiaChi(nd.getDiaChi());
        ndht.setSoDienThoai(nd.getSoDienThoai());
        ndht.setTen(nd.getTen());
        ndht.setUsername(nd.getUsername());
        repo.save(ndht);       
    }
}