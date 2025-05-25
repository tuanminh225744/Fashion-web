package ktpm.projectsoftware.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class SanPhamThuocDonHang {
    @EmbeddedId
    private CompositeKey1 key;

    private int SoLuong;
    private long giaGoc;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "ma_giam_gia_id", nullable = true)
    private MaGiamGia magiamgia;
    public Integer getMaGiamGiaId(){
        if(getMagiamgia()==null)
        return null;
        return getMagiamgia().getID();
    }
}
