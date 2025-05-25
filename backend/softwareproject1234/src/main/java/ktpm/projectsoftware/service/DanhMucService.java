package ktpm.projectsoftware.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ktpm.projectsoftware.entity.DanhMuc;
import ktpm.projectsoftware.repository.DanhMucRepository;

@Service
public class DanhMucService {
    @Autowired
    DanhMucRepository dmRepo;
    public void addDanhMuc(DanhMuc dm){
        dmRepo.save(dm);
    }
    public ArrayList<DanhMuc> getAll(){
        return dmRepo.findAll();
    }
    public DanhMuc findById(int id){
        return dmRepo.findById(id).get();
    }
}
