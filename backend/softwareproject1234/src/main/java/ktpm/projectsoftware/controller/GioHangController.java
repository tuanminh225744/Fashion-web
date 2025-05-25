package ktpm.projectsoftware.controller;

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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ktpm.projectsoftware.Security.JwtService;
import ktpm.projectsoftware.Security.MyUserDetails;
import ktpm.projectsoftware.entity.NguoiDung;
import ktpm.projectsoftware.repository.NguoiDungRepository;
import ktpm.projectsoftware.repository.SanPhamRepository;
import ktpm.projectsoftware.service.DichVuDatHang;
import ktpm.projectsoftware.service.DichVuDonHang;
import ktpm.projectsoftware.service.DichVuNguoiDung;

@RestController
public class GioHangController {
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
   
    

    @PostMapping("/them_vao_gio_hang")
    public ResponseEntity<?> themVaoGioHang(@RequestParam int san_phamid) {
        try{
            return ResponseEntity.ok(dv.themSanPhamVaoGioHang(san_phamid));
        }
         catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/xoa_khoi_gio_hang")
    public ResponseEntity<?> xoa_khoi_gio_hang(@RequestParam int san_phamid) {
        try{
            dv.xoaSanPhamKhoiGioHang(san_phamid);
            return ResponseEntity.ok("xóa thành công");
        }
         catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    //Xem giỏ hàng hiện tại của người dùng
    @GetMapping("/GioHang")
    public ResponseEntity<?> timKiemGioHang(){
        try{
            return ResponseEntity.ok(dv.timKiemGioHang());
        }
         catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
   
}
