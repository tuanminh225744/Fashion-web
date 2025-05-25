package ktpm.projectsoftware.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import jakarta.transaction.Transactional;
import ktpm.projectsoftware.entity.ThongTinCuaHang;

public interface ThongTinCuaHangRepository extends JpaRepository<ThongTinCuaHang,Integer> {
    @Modifying
    @Transactional
    @Query(nativeQuery = true,value="update thong_tin_cua_hang set thong_tin=?1")
    public void capNhatThongTin(String thong_tin);
    @Modifying
    @Transactional
    @Query(nativeQuery = true,value="update thong_tin_cua_hang set chinh_sach=?1")
    public void capNhatChinhSach(String chinh_sach);
}
