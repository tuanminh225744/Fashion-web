package ktpm.projectsoftware.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ktpm.projectsoftware.entity.MaGiamGia;
import ktpm.projectsoftware.service.MaGiamGiaService;

@RestController
public class MaGiamGiaController {
    @Autowired
    MaGiamGiaService mggService;
    @PostMapping("/themMa") //them mã giảm giá,bao gồm ma,ngayHetHan,soLuotConLai,phanTramGiamGia
    public ResponseEntity<?> themMa(@ModelAttribute MaGiamGia mgg,@RequestParam int danhmuc_id){
        try{
            mggService.themMa(mgg,danhmuc_id);
            return ResponseEntity.ok("Thêm mã thành công");
        }
         catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @PostMapping("/huyMa") //hủy mã giảm giá
    public ResponseEntity<?> huyMa(@RequestParam int id){
        try{
            mggService.huyMa(id);
            return ResponseEntity.ok("Hủy mã thành công");
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @GetMapping("/danhSachMa") //danh sách mã giảm giá
    public ResponseEntity<?> danhSach(){
        try{
            return ResponseEntity.ok(mggService.findAll());
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @GetMapping("/maTheoDanhMuc")
    public ResponseEntity<?> maTheoDanhMuc(@RequestParam int danhmuc_id){
        try{
            ArrayList<MaGiamGia> l=mggService.findByDanhMuc(danhmuc_id);
            return ResponseEntity.ok(l);
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @GetMapping("/maDuocChon")
     public ResponseEntity<?> maDuocChon(@RequestParam int danhmuc_id){
        try{
            MaGiamGia l=mggService.maDuocChon(danhmuc_id);
            return ResponseEntity.ok(l);
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
