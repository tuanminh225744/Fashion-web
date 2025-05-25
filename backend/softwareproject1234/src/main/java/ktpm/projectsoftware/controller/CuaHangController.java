package ktpm.projectsoftware.controller;

import ktpm.projectsoftware.service.CuaHangService;
import ktpm.projectsoftware.service.ajaxServlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CuaHangController {
    @Autowired
    CuaHangService chService;
    @PostMapping("/thong_tin") //cập nhật thông tin của hàng
    public ResponseEntity<?> capNhatThongTin(@RequestParam String thong_tin) {
        try{
            chService.capNhatThongTin(thong_tin);
            return ResponseEntity.ok("Cập nhật thành công");
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @PostMapping("/chinh_sach") //cập nhật chính sách
     public ResponseEntity<?> capNhatChinhSach(@RequestParam String chinh_sach) {
        try{
            chService.capNhatChinhSach(chinh_sach);
            return  ResponseEntity.ok("Cập nhật thành công");
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @GetMapping("/getThongTin") // đọc thông tin
    public ResponseEntity<?> getThongTin(){
        try{
           return ResponseEntity.ok(chService.getThongTin());
        }
         catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @GetMapping("/getChinhSach")  //đọc chính sách
    public ResponseEntity<?> getChinhSach(){
        try{
           return ResponseEntity.ok(chService.getChinhSach());
        }
         catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
