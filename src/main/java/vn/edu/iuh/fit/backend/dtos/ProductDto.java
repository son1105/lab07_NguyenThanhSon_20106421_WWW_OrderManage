package vn.edu.iuh.fit.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private long id;
    private String name;
    private String description;
    private String manufacturer;
    private String unit;
    private double price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductDto dto)) return false;
        return getId() == dto.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
