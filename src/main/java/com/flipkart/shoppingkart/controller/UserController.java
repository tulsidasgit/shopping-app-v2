package com.flipkart.shoppingkart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flipkart.shoppingkart.entity.User;
import com.flipkart.shoppingkart.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/all")
	public List<User> getUsers()
	{
		return userService.getUsers();
	}
	
	@GetMapping("/all/sorted")
	public List<User> getUsersSorted()
	{
		return userService.getUsersSorted();
	}
	
	@GetMapping()
	public User getuserById(@RequestParam Long userid)
	{
		return userService.getUserById(userid);
	}
	
	@PostMapping("/add")
	public User addUser(@RequestBody User user)
	{
		return userService.addUser(user);
	}
	
	@PutMapping("/update")
	public User updateUser(@RequestBody User user)
	{
		return userService.addUser(user);
	}
	
	@DeleteMapping("/delete")
	public String deleteUser(@RequestParam Long userid)
	{
		return userService.deleteUser(userid);
	}
	
}

