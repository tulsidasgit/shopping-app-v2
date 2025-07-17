package com.flipkart.shoppingkart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flipkart.shoppingkart.entity.Cart;
import com.flipkart.shoppingkart.entity.Product;
import com.flipkart.shoppingkart.entity.User;
import com.flipkart.shoppingkart.repository.CartRepository;
import com.flipkart.shoppingkart.repository.ProductRepository;
import com.flipkart.shoppingkart.repository.UserRepository;
import com.flipkart.shoppingkart.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@GetMapping("/allcarts")
	public List<Cart> getAllCart()
	{
		return adminService.getAllCarts();
	}
	
	@GetMapping("/cart/id")
	public Cart getCartById(@RequestParam Long cartId)
	{
		return adminService.getCartById(cartId);
	}
	
	@GetMapping("/user/all")
	public List<User> getAllUsers()
	{
		return adminService.getAllUsers();
	}
	
	@GetMapping("/user/id")
	public User getUserById(@RequestParam Long userId)
	{
		return adminService.getUserById(userId);
	}
	
	@GetMapping("/product/all")
	public List<Product> getAllProducts()
	{
		return adminService.getAllProducts();
	}
	
	@GetMapping("/product/id")
	public Product getProductById(@RequestParam Long productId)
	{
		return adminService.getProductById(productId);
	}
	
	@GetMapping("/product/name")
	public Product getProductByName(String productName)
	{
		return adminService.getProductByName(productName);
	}
	
	@PostMapping("product/add")
	public Product addProduct(@RequestBody Product product)
	{
		return adminService.addProduct(product);
				
	}
	
	@DeleteMapping("product/delete")
	public String deleteProduct(@RequestParam Long productId)
	{
		return adminService.deleteProduct(productId);
	}
}
