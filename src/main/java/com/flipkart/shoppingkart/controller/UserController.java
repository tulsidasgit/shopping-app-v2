package com.flipkart.shoppingkart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flipkart.shoppingkart.entity.Cart;
import com.flipkart.shoppingkart.entity.Product;
import com.flipkart.shoppingkart.entity.User;
import com.flipkart.shoppingkart.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	//user registration
	@PostMapping("/register")
	public User registerUser(@RequestBody User user)
	{
		return userService.registerUser(user);
	}
	
	@GetMapping("/id")
	public User getUserById(@RequestParam Long userid)
	{
		return userService.getUserById(userid);
	}
	
	@PutMapping("/update")
	public String updateUserName(@RequestParam Long userid, String username)
	{
		return userService.updateUserName(userid,username);
	}
	
	@GetMapping("/productlist")
	public List<Product> getProductlist()
	{
		return userService.getProductlist();
	}
	
	@GetMapping("/product/id")
	public Product getProductById(@RequestParam Long productid)
	{
		return userService.getProductById(productid);
	}
	
	@GetMapping("/product/name")
	public Product getProductByName(@RequestParam String productname)
	{
		return userService.getProductByName(productname);
	}
	
	@GetMapping("/login")
	public String userLogin(@RequestParam String username, String password)
	{
		return userService.userLogin(username, password);
	}
	
	@PostMapping("/cart/add")
	public String addToCart(String productName, int quantity)
	{
		return userService.addToCart(productName, quantity);
	}
	
	@GetMapping("/cart/get")
	public List<String> getCart()
	{
		return userService.getCart();
	}
	
	@GetMapping("/cart/amount")
	public Double getCartAmount()
	{
		return userService.getCartAmount();
	}
	
	@DeleteMapping("/checkout/cart")
	public String checkoutCart()
	{
		return userService.checkoutCart();
	}
	
	@DeleteMapping("/checkout/items")
	public String checkoutItems()
	{
		return userService.checkoutItems();
	}
	
	@GetMapping("/balance")
	public Double getBalance()
	{
		return userService.getBalance();
	}
	
	@PatchMapping("/balance/add")
	public String addBalance(@RequestParam Double amt)
	{
		return userService.addBalance(amt);
	}
}
