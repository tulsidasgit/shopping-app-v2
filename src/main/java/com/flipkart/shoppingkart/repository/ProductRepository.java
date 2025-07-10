package com.flipkart.shoppingkart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flipkart.shoppingkart.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

}
