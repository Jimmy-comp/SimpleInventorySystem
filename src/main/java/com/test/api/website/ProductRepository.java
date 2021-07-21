package com.test.api.website;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, Integer> {

    List<Product> findProductsByCodeOrderByWeight(String code);

    Product findAllProductByCodeAndLocation(String code, String location);

    Product findProductByCode(String code);

    Product findProductById(String id);
}
