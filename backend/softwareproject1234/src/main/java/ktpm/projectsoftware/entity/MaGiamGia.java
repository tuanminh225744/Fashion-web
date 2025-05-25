package ktpm.projectsoftware.entity;

import java.sql.Date;
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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class MaGiamGia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    @Column(columnDefinition = "NVARCHAR(50)")
    private String ma;
    private Date ngayHetHan;
    private int soLuotConLai;
    private int phanTramGiamGia;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "danh_mucid",nullable=false)
    private DanhMuc danhmuc;
    @JsonIgnore
    @OneToMany(mappedBy="magiamgia")
    private Collection<SanPhamThuocDonHang> sanphamthuocdonhang;
    public int getDanhMucID(){
        return getDanhmuc().getID();
    }
}
