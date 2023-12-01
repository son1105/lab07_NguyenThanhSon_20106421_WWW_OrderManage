package vn.edu.iuh.fit.backend.pks;

import lombok.Getter;
import lombok.Setter;
import vn.edu.iuh.fit.backend.models.Product;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Setter @Getter
public class ProductPricePK implements Serializable {
    private long product;
    private LocalDateTime price_date_time;

    public ProductPricePK(long product, LocalDateTime price_date_time) {
        this.product = product;
        this.price_date_time = price_date_time;
    }

    public ProductPricePK() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductPricePK that)) return false;
        return Objects.equals(getProduct(), that.getProduct()) && Objects.equals(getPrice_date_time(), that.getPrice_date_time());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProduct(), getPrice_date_time());
    }
}
