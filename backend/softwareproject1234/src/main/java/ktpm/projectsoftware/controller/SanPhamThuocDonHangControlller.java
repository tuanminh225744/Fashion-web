package ktpm.projectsoftware.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ktpm.projectsoftware.entity.NguoiDung;
import ktpm.projectsoftware.repository.DonHangRepository;
import ktpm.projectsoftware.repository.NguoiDungRepository;
import ktpm.projectsoftware.service.DichVuNguoiDung;
import ktpm.projectsoftware.service.SanPhamThuocDonHangService;

@RestController
public class SanPhamThuocDonHangControlller {
    @Autowired
    SanPhamThuocDonHangService service;
    @Autowired
    DichVuNguoiDung ndService;
    @Autowired
    DonHangRepository dhRepo;
    @GetMapping("/AllspThuocDonHang")
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(service.findAll());
    }
    @GetMapping("/findByDonHangId")
    public ResponseEntity<?> findByDonHangId(@RequestParam int id){
        NguoiDung nd=ndService.timNguoiDungHienTai();
        if(nd.getVaiTro()!="Chu"&&dhRepo.checkQuyenTruyCap(nd.getID(),id)==null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("không có quyền truy cập đơn hàng");
        return ResponseEntity.ok(service.findByDonHangId(id));
    }
}
