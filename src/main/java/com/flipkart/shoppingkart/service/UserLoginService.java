package com.flipkart.shoppingkart.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flipkart.shoppingkart.entity.Product;
import com.flipkart.shoppingkart.entity.User;
import com.flipkart.shoppingkart.exception.ResourceNotFoundException;
import com.flipkart.shoppingkart.repository.ProductRepository;
import com.flipkart.shoppingkart.repository.UserRepository;

@Service
public class UserLoginService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	public User registerUser(User user)
	{
		return userRepository.save(user);
	}
	
	public User getUserById(Long userid)
	{
		return userRepository.findById(userid).orElseThrow(
				()-> new ResourceNotFoundException("User is not available with id: "+userid)
				);
	}
	
	public String updateUserName(Long userid, String userName)
	{
		Optional userData = userRepository.findById(userid);
		if(userData.isEmpty())
			return "user is not found with id: "+userid;
		User user = (User) userData.get();
		user.setName(userName);
		userRepository.save(user);
		return "Data successfully updated"+user.toString();
	}
	
	public List<Product> getProductlist()
	{
		return productRepository.findAll();
	}
	
	public Product getProductById(Long productid)
	{
		return productRepository.findById(productid).orElseThrow(
				()->new ResourceNotFoundException("product is not available with id: "+productid)
				);
	}
	
	public Product getProductByName(String productName)
	{
		return productRepository.findByNameIgnoreCase(productName).orElseThrow(
				()->new ResourceNotFoundException("product is not available with Name: "+productName)
				);
	}
	
	
}
