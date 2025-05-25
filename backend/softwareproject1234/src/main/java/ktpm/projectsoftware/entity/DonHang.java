package ktpm.projectsoftware.entity;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class DonHang {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int ID;
    private boolean TrangThaiDonHang;
    private boolean NhanHang;
    private boolean daThanhToan;
    private long GiaTriDonHang;
    
    @ManyToOne
    @JoinColumn(name="NguoiDungID",nullable=false)
    @JsonIgnore
    NguoiDung nguoidung;
    public String soDienThoai;
    public String diaChi;
    public int getNguoiDungID(){
        return getNguoidung().getID();
    }
   
}
