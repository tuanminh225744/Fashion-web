package ktpm.projectsoftware.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.twilio.rest.api.v2010.Account;

import ktpm.projectsoftware.Exception.MaSaiHoacHetHan;
import ktpm.projectsoftware.Exception.NguoiDungDaDangKy;
import ktpm.projectsoftware.Exception.SanPhamKhongDu;
import ktpm.projectsoftware.Exception.SoLuongAm;
import ktpm.projectsoftware.Security.JwtService;
import ktpm.projectsoftware.Security.MyUserDetails;
import ktpm.projectsoftware.entity.DonHang;
import ktpm.projectsoftware.entity.NguoiDung;
import ktpm.projectsoftware.entity.SanPham;
import ktpm.projectsoftware.repository.NguoiDungRepository;
import ktpm.projectsoftware.repository.SanPhamRepository;
import ktpm.projectsoftware.service.DichVuDatHang;
import ktpm.projectsoftware.service.DichVuDonHang;
import ktpm.projectsoftware.service.DichVuNguoiDung;
import ktpm.projectsoftware.service.FilesStorageServiceImpl;

@RestController
public class NguoiDungController {
    @Autowired
    DichVuNguoiDung dv;
    @Autowired
    SanPhamRepository spRepo;
    @Autowired
    NguoiDungRepository repo;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    DichVuDatHang dvdh;
    @Autowired
    DichVuDonHang dvdonhang;
    @Autowired
    JwtService jwtService;
    @Autowired
    FilesStorageServiceImpl fileService;
    @PostMapping("/dang_ky") //cần 2 thông tin là ten và matKhau(ten thật ra là email,nhưng hiện giờ hơi khó sửa)
    public ResponseEntity<?> dangKy(@RequestBody NguoiDung nd) throws Exception {
        try{
            return ResponseEntity.ok(dv.KhachHangChuaDangKy(nd));
        }
         catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
     @PostMapping("/reset_password")
    public ResponseEntity<?> resst_password(@RequestParam String email){
        try{
            dv.DatLaiMatKhau(email);
            return ResponseEntity.ok("Đã gửi mã xác nhận");
        }
         catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @PostMapping("/confirm_reset_password") //mxn là mã xác nhận được gửi tới email
    public ResponseEntity<?> confirm(String email,String mxn,String new_pass){
        try{
            dv.DatLaiMatKhauThanhCong(email, mxn, new_pass);
            return ResponseEntity.ok("Đặt lại mật khẩu thành công");
        }
         catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }
    @GetMapping("/NguoiDungHienTai")
    public ResponseEntity<?> nguoiDungHienTai(){
        try{
            return ResponseEntity.ok(dv.timNguoiDungHienTai());
        }
         catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @PostMapping("/xac_nhan_dang_ky") //cần 2 thông tin là ten và maXacNhan
    public ResponseEntity<?> xacNhan(@RequestBody NguoiDung nd) throws Exception {
        try{
            return ResponseEntity.ok(dv.NguoiDungDangKyThanhCong(nd.getTen(), nd.getMaXacNhan()));
        }
         catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/signin")//ten và matKhau
    public ResponseEntity<?> login(@RequestBody NguoiDung nd) {
        try {
            // Xác thực thông tin đăng nhập
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            nd.getTen(),
                            nd.getMatKhau()));

            // Nếu xác thực thành công, tạo token và trả về


            // Nếu xác thực thành công
            if (authentication.isAuthenticated()) {
                // Lấy thông tin Account từ đối tượng xác thực
                SecurityContextHolder.getContext().setAuthentication(authentication);
                MyUserDetails account = (MyUserDetails) authentication.getPrincipal();

                // Tạo JWT token
                String token = jwtService.generateToken(account.getUsername());
                Map<String, Object> response = new HashMap<>();
                response.put("user",dv.timNguoiDungHienTai());
                response.put("token", token);
                // Trả về token và role
                return ResponseEntity.ok(
                        response
                        );
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Invalid username or password");
            }
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Username not found");
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Incorrect password");
        } catch (LockedException | DisabledException | AccountExpiredException | CredentialsExpiredException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("An error occurred: " + e.getMessage());
        }
    }
    @PostMapping("/thayavt")
    public ResponseEntity<?> thayAvt(@RequestParam MultipartFile image){
        try{
            String name=fileService.generateRandomString(image.getOriginalFilename());
            fileService.save(image,name);
            NguoiDung nd=dv.timNguoiDungHienTai();
            nd.setSourceImage(name);
            repo.save(nd);
            return ResponseEntity.ok("Thay avatar thành công");
        }
          catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        
    }
    @PostMapping("/updateUser")
    public ResponseEntity<?> update(@RequestBody NguoiDung nd){
        try{
            dv.update(nd);
            return ResponseEntity.ok("cập nhật thành công");
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}