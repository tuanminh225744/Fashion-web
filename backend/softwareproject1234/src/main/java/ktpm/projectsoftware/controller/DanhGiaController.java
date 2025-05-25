package ktpm.projectsoftware.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ktpm.projectsoftware.entity.DanhGia;
import ktpm.projectsoftware.service.DichVuDanhGia;
import ktpm.projectsoftware.service.FilesStorageServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class DanhGiaController {
    @Autowired
    FilesStorageServiceImpl fileservice;
    @Autowired
    DichVuDanhGia dvdg;

    @PostMapping("/danh_gia") //luu đánh giá sản phẩm
    public ResponseEntity<?> luuDanhGia(@RequestParam String binh_luan, @RequestParam MultipartFile hinh_anh,
            @RequestParam int sao, @RequestParam int san_phamid) {
        try{
            if (hinh_anh != null) {
                String s = fileservice.generateRandomString(hinh_anh.getOriginalFilename());
                fileservice.save(hinh_anh,s);
                return ResponseEntity.ok(dvdg.luuDanhGia(binh_luan, s,  sao, san_phamid));
            }
            return ResponseEntity.ok(dvdg.luuDanhGia(binh_luan, null,  sao, san_phamid));
        }
        catch(Exception e){
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @GetMapping("/danh_sach_danh_gia") //danh sách các comment của 1 sản phẩm,ở 
//  phía frontendchỉ cần lấy id sản phẩm,số sao,comment và ảnh bình luận nếu có
    public ResponseEntity<?> danhSach(int sanpham_id){
        try{
            return ResponseEntity.ok(dvdg.danhGiaSanPham(sanpham_id));
        }
         catch(Exception e){
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
