package com.flipkart.shoppingkart.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.flipkart.shoppingkart.entity.User;
import com.flipkart.shoppingkart.repository.UserRepository;
import com.flipkart.shoppingkart.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@InjectMocks
	private UserService userService;
	
	@Mock
	private UserRepository userRepository;
	
	@Test
	void getUserById_notFound()
	{
		when(userRepository.findById(1L)).thenReturn(Optional.empty());
		assertThrows(NoSuchElementException.class, ()->{userService.getUserById(1L);});
	}
	
	@Test
	void getUserById_UserFound()
	{
		User user = new User();
		user.setId(1L);
		user.setName("Roshan");
		when(userRepository.findById(1L)).thenReturn(Optional.of(user));
		//assertNotNull(userRepository.findById(1L));
		assertEquals("Roshan",userService.getUserById(1L).getName());
		assertNotNull(userService.getUserById(1L));
		verify(userRepository,times(2)).findById(1L);
	}
	
	@Test
	void getUsers_Test()
	{
		User user1 = new User();
		user1.setId(1L);
		user1.setName("Roshan");
		
		User user2 = new User();
		user2.setId(2L);
		user2.setName("Patil");
		
		List<User> userlist = List.of(user1,user2);
		
		when(userRepository.findAll()).thenReturn(userlist);
		assertEquals(2,userService.getUsers().size());
		//verify(userRepository, times(1)).count();   // this will not work
		verify(userRepository).findAll();
	}
	
	@Test
	void addUser_Test()
	{
		User user = new User();
		user.setId(1L);
		user.setName("Roshan");
		
		when(userRepository.save(user)).thenReturn(user);
		User result = userService.addUser(user);
		assertEquals("Roshan",result.getName());
		verify(userRepository).save(user);
	}
	
	@Test
	void deleteUser_UserExist()
	{
		when(userRepository.existsById(1L)).thenReturn(true);
		assertEquals("user deleted from database",userService.deleteUser(1L));
		
		verify(userRepository).existsById(1L);
		verify(userRepository).deleteById(1L);
	}
}
