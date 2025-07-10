package com.flipkart.shoppingkart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flipkart.shoppingkart.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{
	
	List<Cart>findByProductId(Long productid);
}
