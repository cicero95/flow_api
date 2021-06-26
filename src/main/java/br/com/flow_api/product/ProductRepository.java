package br.com.flow_api.product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findById(long id);

    @Query(value = "SELECT * FROM tbl_products WHERE productquantity <= negativebalance", nativeQuery = true)
    List<Product> findByNegativebalance();

    @Query(value = "SELECT * FROM tbl_products " +
            " WHERE expirationdate <= DATE_ADD(current_timestamp(), INTERVAL 30 DAY) ORDER BY expirationdate ASC", nativeQuery = true)
    List<Product> findByExpirationDate();
}
