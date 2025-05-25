package ktpm.projectsoftware.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ktpm.projectsoftware.entity.DonHang;
import ktpm.projectsoftware.repository.DonHangRepository;
import ktpm.projectsoftware.service.ajaxServlet;

@Controller
public class ThanhToan {
    @Autowired
    ajaxServlet aj;
    @Autowired
    DonHangRepository dhRepo;

    @PostMapping("/thanhtoan")
    public String thanhtoan(HttpServletRequest req, HttpServletResponse res) {
        try {
            DonHang dh = dhRepo.findById(Integer.parseInt(req.getParameter("don_hangid")));
            aj.doPost(req, res, Long.toString(dh.getGiaTriDonHang()));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:" + aj.getPaymentUrl();
    }
}