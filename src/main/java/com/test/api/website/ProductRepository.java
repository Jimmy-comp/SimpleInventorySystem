package com.test.api.website;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, Integer> {

    // Find Products by Code
    List<Product> findProductsByCodeOrderByWeight(String code);

    // Find All Products by Code and Location
    Product findAllProductByCodeAndLocation(String code, String location);

    // Find Product by Id
    Product findProductById(String id);
}
