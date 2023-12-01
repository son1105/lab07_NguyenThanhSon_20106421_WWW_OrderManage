package vn.edu.iuh.fit.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.edu.iuh.fit.backend.models.Product;
import vn.edu.iuh.fit.backend.models.ProductPrice;
import vn.edu.iuh.fit.backend.pks.ProductPricePK;

import java.util.List;

public interface ProductPriceRepository extends JpaRepository<ProductPrice, ProductPricePK> {
    @Query("SELECT p from ProductPrice p where p.product.id=:id order by p.price_date_time desc")
    List<ProductPrice> findByProductOrderByPrice_date_time(@Param("id") long id);
}