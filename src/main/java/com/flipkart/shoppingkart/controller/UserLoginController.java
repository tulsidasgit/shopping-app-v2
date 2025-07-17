package com.flipkart.shoppingkart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flipkart.shoppingkart.entity.Product;
import com.flipkart.shoppingkart.entity.User;
import com.flipkart.shoppingkart.service.UserLoginService;

@RestController
@RequestMapping("/user")
public class UserLoginController {
	
	@Autowired
	private UserLoginService userLoginService;
	
	@PostMapping("/register")
	public User registerUser(@RequestBody User user)
	{
		return userLoginService.registerUser(user);
	}
	
	@GetMapping("/id")
	public User getUserById(@RequestParam Long userid)
	{
		return userLoginService.getUserById(userid);
	}
	
	@PutMapping("/update")
	public String updateUserName(@RequestParam Long userid, String username)
	{
		return userLoginService.updateUserName(userid,username);
	}
	
	@GetMapping("/productlist")
	public List<Product> getProductlist()
	{
		return userLoginService.getProductlist();
	}
	
	@GetMapping("/product/id")
	public Product getProductById(@RequestParam Long productid)
	{
		return userLoginService.getProductById(productid);
	}
	
	@GetMapping("/product/name")
	public Product getProductByName(@RequestParam String productname)
	{
		return userLoginService.getProductByName(productname);
	}
}
