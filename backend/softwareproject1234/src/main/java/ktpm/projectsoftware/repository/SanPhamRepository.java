package ktpm.projectsoftware.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import ktpm.projectsoftware.entity.DanhMuc;
import ktpm.projectsoftware.entity.SanPham;

import java.util.List;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, Integer> {
    ArrayList<SanPham> findAll();
    ArrayList<SanPham> findByConBayBan(boolean b);
    SanPham findById(int id);
    @Modifying
    @Transactional
    @Query(nativeQuery=true,value="update san_pham set con_bay_ban=0 where id=?1")
    public void ngungBayBan(int id);
    
    public ArrayList<SanPham> findByDanhmuc(DanhMuc dm);
    
}
