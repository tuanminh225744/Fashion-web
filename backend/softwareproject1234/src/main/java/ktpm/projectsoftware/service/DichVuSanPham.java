package ktpm.projectsoftware.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ktpm.projectsoftware.entity.DanhMuc;
import ktpm.projectsoftware.entity.SanPham;
import ktpm.projectsoftware.repository.DanhGiaRepository;
import ktpm.projectsoftware.repository.DanhMucRepository;
import ktpm.projectsoftware.repository.SanPhamRepository;

@Service
public class DichVuSanPham {
    @Autowired
    SanPhamRepository spRepo;
    @Autowired
    DanhMucRepository dmRepo;
    @Autowired
    DanhGiaRepository dgRepo;

    public boolean timKiem(SanPham sp, String tuKhoa, String danhMuc, String sao, String gia) {
        if (sp.getTenSanPham().toLowerCase().contains(tuKhoa.toLowerCase()) == false)
            return false;
        if (danhMuc.equals("Không") == false) {
            DanhMuc dm = dmRepo.findBytenDanhMuc(danhMuc);
            if (sp.getDanhmuc().getID() != dm.getID())
                return false;
        }
        if (sao.equals("Không") == false) {
            int i = (int) dgRepo.SaoTrungBinh(sp.getID());
            if (i != Integer.parseInt(sao))
                return false;
        }
        if (gia.equals("Không") == false) {
            if (sp.getGia() > Long.parseLong(gia))
                return false;
        }
        return true;
    }
    public ArrayList<SanPham> findByDanhMuc(int id){
        DanhMuc dm=dmRepo.findById(id).get();
        return spRepo.findByDanhmuc(dm);
    }
    public ArrayList<SanPham> findByDanhMucGiaGiamDan(int danhMuc_id){
        ArrayList<SanPham> sp=spRepo.findByDanhmuc(dmRepo.findById(danhMuc_id).get());
        sp.sort(Comparator.comparingDouble(SanPham::getGia));
        Collections.reverse(sp);
        return sp;
    }
    public ArrayList<SanPham> findByDanhMucGiaTangDan(int danhMuc_id){
        ArrayList<SanPham> sp=spRepo.findByDanhmuc(dmRepo.findById(danhMuc_id).get());
        sp.sort(Comparator.comparingDouble(SanPham::getGia));
        return sp;
    }
    public ArrayList<SanPham> timKiemSanPham(String tuKhoa, String danhMuc, String sao, String gia) {
        ArrayList<SanPham>l1=spRepo.findAll();

        ArrayList<SanPham> l2 = new ArrayList<SanPham>();
        for (SanPham sp : l1) {
            System.out.println(sp.getID() + " " + sp.getTenSanPham());
        }
        for (SanPham sp : l1) {
            if (timKiem(sp, tuKhoa, danhMuc, sao, gia))
                l2.add(sp);
        }
        return l2;
    }
    public ArrayList<SanPham> findAll(){
        return spRepo.findAll();
    }
    public ArrayList<SanPham> sortByPriceAsc(){
        ArrayList<SanPham>l1=spRepo.findAll();
        l1.sort(Comparator.comparingDouble(SanPham::getGia));
        return l1;
    }
    public ArrayList<SanPham> sortByPriceDesc(){
        ArrayList<SanPham>l1=spRepo.findAll();
        l1.sort(Comparator.comparingDouble(SanPham::getGia));
        Collections.reverse(l1);
        return l1;
    }
    public SanPham chiTietSanPham(int id){    
        return spRepo.findById(id);
    }
    public void themSanPham(SanPham sp){
        sp.setConBayBan(true);
        spRepo.save(sp);
    }
    public void ngungBayBan(int id){
        spRepo.ngungBayBan(id);
    }
}
