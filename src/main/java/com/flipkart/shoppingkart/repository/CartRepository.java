package com.flipkart.shoppingkart.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flipkart.shoppingkart.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{
	
	Optional<Cart> findByProductId(Long productid);
	Optional<Cart> findByUserId(Long userid);
	List<Cart> findAllByUserId(Long userid);
	void deleteAllByUserId(Long userid);
	void deleteAllByProductIdIn(List<Long> userid);
}
