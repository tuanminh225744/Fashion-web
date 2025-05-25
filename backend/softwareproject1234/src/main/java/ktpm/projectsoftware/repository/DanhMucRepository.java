package ktpm.projectsoftware.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ktpm.projectsoftware.entity.DanhMuc;

@Repository
public interface DanhMucRepository extends JpaRepository<DanhMuc, Integer> {
    DanhMuc findBytenDanhMuc(String ten);
    ArrayList<DanhMuc> findAll();
}
