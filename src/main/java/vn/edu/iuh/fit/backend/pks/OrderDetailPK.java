package vn.edu.iuh.fit.backend.pks;

import lombok.Getter;
import lombok.Setter;
import vn.edu.iuh.fit.backend.models.Order;
import vn.edu.iuh.fit.backend.models.Product;

import java.io.Serializable;
import java.util.Objects;

@Setter @Getter
public class OrderDetailPK implements Serializable {
    private long order;
    private long product;

    public OrderDetailPK(long order, long product) {
        this.order = order;
        this.product = product;
    }

    public OrderDetailPK() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderDetailPK that)) return false;
        return getOrder() == that.getOrder() && getProduct() == that.getProduct();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrder(), getProduct());
    }
}
