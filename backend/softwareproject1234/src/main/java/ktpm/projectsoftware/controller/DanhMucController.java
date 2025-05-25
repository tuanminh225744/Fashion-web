package ktpm.projectsoftware.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ktpm.projectsoftware.service.DanhMucService;

@RestController
public class DanhMucController{
    @Autowired
    DanhMucService service;
    @GetMapping("/AllDanhMuc") //Danh sách các danh mục,frontend chỉ cần lấy id và tên danh mục
    public ResponseEntity<?> findAll(){
        try{
            return ResponseEntity.ok(service.getAll());
        }
          catch(Exception e){
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @GetMapping("/findDanhMucById") //tìm kiếm danh mục bằng id frontend chỉ cần lấy id và tên danh mục
    public ResponseEntity<?> findById(@RequestParam int id){
        try{
            return ResponseEntity.ok(service.findById(id));
        }
          catch(Exception e){
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}