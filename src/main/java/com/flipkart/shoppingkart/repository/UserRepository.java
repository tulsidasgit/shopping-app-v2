package com.flipkart.shoppingkart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flipkart.shoppingkart.entity.User;

@Repository		// This will map the the interface as Spring managed DAO bean and handle exceptions
public interface UserRepository extends JpaRepository<User,Long>{
	// JpaRepository will provide methods for 
		//save, findById, count, deleteById, findAll(), etc
}
