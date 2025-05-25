package ktpm.projectsoftware.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ktpm.projectsoftware.repository.DonHangRepository;

@RestController
public class ThanhToanResult {
    @Autowired
    DonHangRepository dhRepo;
      @PostMapping("/ketquathanhtoan")
    public ResponseEntity<?> ketqua(@RequestParam int vnp_OrderInfo,@RequestParam int vnp_TransactionStatus){
        try{
            if(vnp_TransactionStatus==0){
                dhRepo.ThanhToanThanhCong(vnp_OrderInfo);
                return ResponseEntity.ok("Thanh toán thành công");
            }
            else
            return ResponseEntity.ok("Thanh toán không thành công");
        }
         catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
