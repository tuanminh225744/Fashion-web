package ktpm.projectsoftware.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ktpm.projectsoftware.entity.DanhGia;
import ktpm.projectsoftware.entity.NguoiDung;
import ktpm.projectsoftware.entity.SanPham;
import ktpm.projectsoftware.repository.DanhGiaRepository;
import ktpm.projectsoftware.repository.NguoiDungRepository;
import ktpm.projectsoftware.repository.SanPhamRepository;

@Service
public class DichVuDanhGia {
    @Autowired
    DichVuNguoiDung dvnd;
    @Autowired
    DanhGiaRepository dgRepo;
    @Autowired
    SanPhamRepository spRepo;
    @Autowired 
    NguoiDungRepository ndRepo;
    public  DanhGia luuDanhGia(String binh_luan,String nguon_anh,int sao,int san_phamid){
        NguoiDung nd=dvnd.timNguoiDungHienTai();
        return dgRepo.save(new DanhGia(0,binh_luan,nguon_anh,sao,nd,spRepo.findById(san_phamid)));
    }
    public ArrayList<DanhGia> danhGiaSanPham(int sanphamid){
        SanPham sp=spRepo.findById(sanphamid);
        return dgRepo.findBySanpham(sp);
    }
    
}
