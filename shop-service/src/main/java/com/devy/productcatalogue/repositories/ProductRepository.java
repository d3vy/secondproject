package com.devy.productcatalogue.repositories;

import com.devy.productcatalogue.models.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {

    @Query(name = "Product.findAllByTitleLikeIgnoreCase", nativeQuery = true)
    Iterable<Product> findAllByTitleLikeIgnoreCase(String filter);

}
