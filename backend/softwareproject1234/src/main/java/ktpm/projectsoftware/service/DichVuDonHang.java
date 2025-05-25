package ktpm.projectsoftware.service;

import java.util.ArrayList;

import org.hibernate.metamodel.model.domain.internal.DomainModelHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ktpm.projectsoftware.entity.DonHang;
import ktpm.projectsoftware.entity.NguoiDung;
import ktpm.projectsoftware.repository.DonHangRepository;

@Service
public class DichVuDonHang {
    @Autowired
    DichVuNguoiDung dvnd;
    @Autowired
    DonHangRepository dhRepo;
    @Autowired
    EmailSender sender;

    public ArrayList<DonHang> danhSachDonHangNguoiDung() {
        NguoiDung nd = dvnd.timNguoiDungHienTai();
        return dhRepo.timKiemBangNguoiDung(nd.getID());
    }
    public ArrayList<DonHang> findAll(){
        return dhRepo.findAll();
    }
    public void huyDonHang(int nguoi_dungid) {
        dhRepo.huyDonHang(nguoi_dungid);
    }
    public void tuChoiDonHang(int id){
        dhRepo.tuChoiDonHang(id);
    }
    public void xacNhanNhanHang(int id){
        dhRepo.xacNhanNhanHang(id);
    }
    public void huyThanhToan(int don_hangid) {
        NguoiDung nd = dvnd.timNguoiDungHienTai();
        DonHang dh = dhRepo.findById(don_hangid);
        if (dh.isDaThanhToan() && !dh.isNhanHang()) {
            sender.sendEmail("hoan tien thanh cong", nd.getTen());
            dh.setDaThanhToan(false);
            dhRepo.save(dh);
        }

    }

    public Long tongDoanhThu() {
        return dhRepo.TongDoanhThu();
    }
}
