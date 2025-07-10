package com.flipkart.shoppingkart.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.flipkart.shoppingkart.entity.User;
import com.flipkart.shoppingkart.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public List<User> getUsers()
	{
		return userRepository.findAll();
	}
	
	public List<User> getUsersSorted()
	{
		return userRepository.findAll().stream().sorted(Comparator.comparing(User::getId)).collect(Collectors.toList());
	}
	
	public User getUserById(Long userId)
	{
		return userRepository.findById(userId).orElseThrow();
	}
	
	public User addUser(User user)
	{
		return userRepository.save(user);
	}
	
	public String deleteUser(Long userid)
	{
		if(userRepository.existsById(userid))
		{
			userRepository.deleteById(userid);
			return "user deleted from database";
		}
		return "no user found";
	}
}

