package ktpm.projectsoftware.entity;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.ToString;

@Data
@Entity

public class SanPham {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private String tenSanPham;
    @Column(columnDefinition = "NVARCHAR(500)")
    private String SourceHinhAnh;
    @Column(columnDefinition = "NVARCHAR(500)")
    private String MoTa;
    private long Gia;
    private int soLuongHienTai;
    private boolean conBayBan;
    @ToString.Exclude
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "DanhMucID", nullable = false)
    private DanhMuc danhmuc;
    @JsonIgnore
    @ManyToMany(mappedBy = "sanpham")
    Collection<NguoiDung> nguoidung;
    @JsonIgnore
    @OneToMany(mappedBy = "sanpham")
    Collection<DanhGia> danhgia;
    public int getDanhMucID(){
        return getDanhmuc().getID();
    }
}
