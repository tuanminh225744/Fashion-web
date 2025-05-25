package ktpm.projectsoftware.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ktpm.projectsoftware.entity.DanhGia;
import ktpm.projectsoftware.entity.SanPham;

@Repository
public interface DanhGiaRepository extends JpaRepository<DanhGia, Integer> {
    @Query(nativeQuery = true, value = "select avg(sao) from danh_gia where san_phamid=?1")
    public float SaoTrungBinh(int spID);

    public DanhGia save(DanhGia dg);
    public ArrayList<DanhGia> findBySanpham(SanPham sp);
}
