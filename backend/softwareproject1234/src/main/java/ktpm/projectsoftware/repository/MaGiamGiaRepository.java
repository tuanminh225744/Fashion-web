package ktpm.projectsoftware.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import ktpm.projectsoftware.entity.DanhMuc;
import ktpm.projectsoftware.entity.MaGiamGia;
@Repository
public interface MaGiamGiaRepository extends JpaRepository<MaGiamGia,Integer> {
    @Query(nativeQuery=true,value="select * from ma_giam_gia where danh_mucid=?1 and ngay_het_han>CURDATE() and so_luot_con_lai>0 order by phan_tram_giam_gia desc limit 1")
    public MaGiamGia timKiemMaGiamGia(int id);
    @Modifying
    @Transactional
    @Query(nativeQuery = true,value="update ma_giam_gia set ngay_het_han=CURDATE() WHERE id=?1")
    public void huyMa(int id);
    public ArrayList<MaGiamGia> findAll();
    public ArrayList<MaGiamGia> findByDanhmuc(DanhMuc dm);
}
