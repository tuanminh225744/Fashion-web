package ktpm.projectsoftware.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ktpm.projectsoftware.entity.SanPhamThuocDonHang;
import ktpm.projectsoftware.repository.SanPhamThuocDonHangRepository;

@Service
public class SanPhamThuocDonHangService {
    @Autowired
    SanPhamThuocDonHangRepository repo;
    public ArrayList<SanPhamThuocDonHang> findAll(){
        return repo.findAll();
    }
    public ArrayList<SanPhamThuocDonHang> findByDonHangId(int id){
        return repo.findByDonHangId(id);
    }
}
