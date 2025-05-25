package ktpm.projectsoftware.controller;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

import ktpm.projectsoftware.entity.DonHang;
import ktpm.projectsoftware.entity.SanPham;
import ktpm.projectsoftware.service.DichVuDatHang;
import ktpm.projectsoftware.service.DichVuDonHang;
import ktpm.projectsoftware.service.ajaxServlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class DonHangController {
    @Autowired
    private ajaxServlet httt;
    @Autowired
    DichVuDonHang dvdh;
    @Autowired
    DichVuDatHang dvdonhang;
    //đặt hàng từ giỏ hàng,dssp là list id của sản phẩm,slsp là danh sách số lượng tương ứng mà người dùng nhập vào
    //vd như dssp là {1,2},slsp là {3,5} có nghĩa là sp id 1 mua 3 cái,id 2 mua 5
    @PostMapping("dat_hang") 
    public ResponseEntity<?> datHang(@RequestParam ArrayList<Integer> dssp,@RequestParam ArrayList<Integer> slsp,
    @RequestParam String so_dien_thoai,@RequestParam String dia_chi)throws Exception{  
        try{ 
            System.out.println(dssp.size()+""+slsp.size());     
            dvdonhang.datHang(dssp,slsp,so_dien_thoai,dia_chi);
            return ResponseEntity.ok("đặt hàng thành công");
        }
         catch(Exception e){
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
            
    }
    //hủy đơn theo id
     @PostMapping("/huydonhang")
     public ResponseEntity<?> huyDonHang(@RequestParam int don_hangid) {
        try{
            dvdh.huyDonHang(don_hangid);
            return  ResponseEntity.ok("hủy đơn thành công");
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    //toàn bộ danh sách đơn hàng được đặt
    @GetMapping("/fulldanhsachdonhang")
    public ResponseEntity<?> fuldanhsach(){
        try{
            return  ResponseEntity.ok(dvdh.findAll());
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    //danh sách đơn hàng của người dùng hiện tại
    @GetMapping("/danhsachhientai")
     public ResponseEntity<?> danhSachHienTai() {
        try{
            
            return  ResponseEntity.ok(dvdh.danhSachDonHangNguoiDung());
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    //hủy thanh toán đơn hàng
    @PostMapping("/huyThanhToan")
     public ResponseEntity<?> huyThanhToan(@RequestParam int don_hangid) {
        try{
            dvdh.huyThanhToan(don_hangid);
            return  ResponseEntity.ok("hủy thanh toán thành công");
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    
   //Xác nhận người dùng đã nhận hàng
    @PostMapping("/xacNhanNhanHang")
    public ResponseEntity<?> xacNhanNhanHang(@RequestParam int id){
        try{
            dvdh.xacNhanNhanHang(id);
            return    ResponseEntity.ok("xác nhận thành công");
        }
         catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
     
    }
    //Tổng doanh thu của cửa hàng
    @GetMapping("/TongDoanhThu")
    public ResponseEntity<?> tongDoanhThu(){
        return ResponseEntity.ok(dvdh.tongDoanhThu());
    }
}