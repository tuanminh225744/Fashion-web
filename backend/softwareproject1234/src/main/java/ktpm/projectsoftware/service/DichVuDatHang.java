package ktpm.projectsoftware.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ktpm.projectsoftware.Exception.SanPhamKhongDu;
import ktpm.projectsoftware.Exception.SoLuongAm;
import ktpm.projectsoftware.entity.CompositeKey1;
import ktpm.projectsoftware.entity.DonHang;
import ktpm.projectsoftware.entity.MaGiamGia;
import ktpm.projectsoftware.entity.NguoiDung;
import ktpm.projectsoftware.entity.SanPham;
import ktpm.projectsoftware.entity.SanPhamThuocDonHang;
import ktpm.projectsoftware.repository.DonHangRepository;
import ktpm.projectsoftware.repository.MaGiamGiaRepository;
import ktpm.projectsoftware.repository.SanPhamRepository;
import ktpm.projectsoftware.repository.SanPhamThuocDonHangRepository;

@Service
public class DichVuDatHang {
    @Autowired
    private SanPhamRepository spRepo;
    @Autowired
    private DonHangRepository dhRepo;
    @Autowired
    private DichVuNguoiDung dv;
    @Autowired
    private SanPhamThuocDonHangRepository spdhRepo;
    @Autowired
    MaGiamGiaRepository mggRepo;

    public void datHang(ArrayList<Integer> dssp, ArrayList<Integer> slsp, String so_dien_thoai, String dia_chi)
            throws Exception {
        long tong_so_tien = 0;
        NguoiDung nd = dv.timNguoiDungHienTai();
        ArrayList<Integer> not_enough=new ArrayList<Integer>();
        ArrayList<SanPhamThuocDonHang> l = new ArrayList<SanPhamThuocDonHang>();
        for (int i = 0; i < dssp.size(); i++) {
            SanPham sp = spRepo.findById(dssp.get(i)).get();
            if (slsp.get(i) > sp.getSoLuongHienTai())
               not_enough.add(dssp.get(i));
            if (slsp.get(i) < 0)
                throw new SoLuongAm("Sản phẩm " + sp.getTenSanPham() + " không được có số lượng âm");
            sp.setSoLuongHienTai(sp.getSoLuongHienTai() - slsp.get(i));
            MaGiamGia mgg = mggRepo.timKiemMaGiamGia(sp.getDanhmuc().getID());
            l.add(new SanPhamThuocDonHang(new CompositeKey1(0, sp.getID()), slsp.get(i), sp.getGia(), mgg));
            if (mgg == null)
                tong_so_tien += sp.getGia() * slsp.get(i);
            else {
                mgg.setSoLuotConLai(mgg.getSoLuotConLai() - 1);
                tong_so_tien += sp.getGia() * slsp.get(i) * (1 - mgg.getPhanTramGiamGia() / 100.00);
                mggRepo.save(mgg);
            }
        }
        if(not_enough.size()!=0)
            throw new SanPhamKhongDu("Cac san pham voi id "+not_enough.toString()+" Khong con du so luong");
        System.out.println(tong_so_tien);
        DonHang dh = dhRepo.save(new DonHang(0, true, false, false, tong_so_tien, nd,so_dien_thoai,dia_chi));
        for (SanPhamThuocDonHang spdh : l) {
            CompositeKey1 ck1 = spdh.getKey();
            ck1.setDonHangID(dh.getID());
        }
        spdhRepo.saveAll(l);
    }

    public long doanhThuSanPham(int san_phamid) {
        ArrayList<SanPhamThuocDonHang> l = spdhRepo.danhSachBanHang();
        long doanhThu = 0;
        for (SanPhamThuocDonHang spdh : l) {
            if(spdh.getKey().getSanPhamID()==san_phamid){
                if (spdh.getMagiamgia() == null)
                    doanhThu += spdh.getGiaGoc() * spdh.getSoLuong();
                else
                    doanhThu += spdh.getGiaGoc() * spdh.getSoLuong() * (1 - (float)spdh.getMagiamgia().getPhanTramGiamGia()/100);
            }
        }

        return doanhThu;
    }

}