package ktpm.projectsoftware.entity;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
@Data
@Entity
public class DanhMuc{
      @Id
      @GeneratedValue(strategy=GenerationType.IDENTITY)
      private int ID;
      @Column(columnDefinition = "NVARCHAR(50)",unique=true)
      private String tenDanhMuc;
      @JsonIgnore
      @OneToMany(mappedBy="danhmuc")
      Collection<SanPham> sanpham;
      @JsonIgnore
      @OneToMany(mappedBy="danhmuc")
      private Collection<MaGiamGia> magiamgia;
}