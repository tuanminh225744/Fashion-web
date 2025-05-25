package ktpm.projectsoftware.entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Embeddable
@AllArgsConstructor
public class CompositeKey1 implements Serializable {

    private int DonHangID;
    private int SanPhamID;

    // Default constructor
    public CompositeKey1() {}

    // Getters and Setters

    // Implement equals() and hashCode()
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CompositeKey1))
            return false;
        CompositeKey1 ck = (CompositeKey1) o;
        return ck.getDonHangID() == DonHangID && ck.getSanPhamID() == SanPhamID;

    }

}