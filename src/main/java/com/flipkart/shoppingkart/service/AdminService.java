package com.flipkart.shoppingkart.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flipkart.shoppingkart.entity.Cart;
import com.flipkart.shoppingkart.entity.Product;
import com.flipkart.shoppingkart.entity.User;
import com.flipkart.shoppingkart.exception.ResourceNotFoundException;
import com.flipkart.shoppingkart.repository.CartRepository;
import com.flipkart.shoppingkart.repository.ProductRepository;
import com.flipkart.shoppingkart.repository.UserRepository;

@Service
public class AdminService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CartRepository cartRepository; 
	
	public List<Cart> getAllCarts()
	{
		return cartRepository.findAll();
	}
	
	public Cart getCartById(Long cartId)
	{
		return cartRepository.findById(cartId).orElseThrow(
				()-> new ResourceNotFoundException("cart not found with id: "+cartId)
				);
	}
	
	public List<User> getAllUsers()
	{
		return userRepository.findAll();
	}
	
	public User getUserById(Long userId)
	{
		return userRepository.findById(userId).orElseThrow(
				()->new ResourceNotFoundException("User not found with id: "+userId)
				);
	}
	
	public List<Product> getAllProducts()
	{
		return productRepository.findAll();
	}
	
	public Product getProductById(Long productId)
	{
		return productRepository.findById(productId).orElseThrow(
				()-> new ResourceNotFoundException("Product is not available with Id: "+productId)
				);
	}
	
	public Product getProductByName(String productName)
	{
		return productRepository.findByNameIgnoreCase(productName).orElseThrow(
				()-> new ResourceNotFoundException("product is not available with name: "+productName)
				);
	}
	
	public Product addProduct(Product product)
	{
		Optional<Product> availableProduct = productRepository.findByNameIgnoreCase(product.getName());
		
		if(availableProduct.isEmpty())
		return productRepository.save(product);
		
		product.setQuantity(product.getQuantity()+availableProduct.get().getQuantity());
		product.setId(availableProduct.get().getId());
		return productRepository.save(product);		
		
	}
	
	public String deleteProduct(Long productId)
	{
		Optional<Product> product = productRepository.findById(productId);
		if(product.isEmpty())
			return "product is not available";
		productRepository.deleteById(productId);
		return "Product deleted from DB with id: "+productId;
	}
}
