package com.flipkart.shoppingkart.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flipkart.shoppingkart.entity.Cart;
import com.flipkart.shoppingkart.entity.Product;
import com.flipkart.shoppingkart.entity.User;
import com.flipkart.shoppingkart.exception.ResourceNotFoundException;
import com.flipkart.shoppingkart.repository.CartRepository;
import com.flipkart.shoppingkart.repository.ProductRepository;
import com.flipkart.shoppingkart.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	private User user = null;
	
	Scanner scanner = new Scanner(System.in);
	
	//user registration
	public User registerUser(User user)
	{
		return userRepository.save(user);
	}
	
	//userLogin
	public String userLogin(String username, String password)
	{
		Optional userOpt = userRepository.findByUsernameIgnoreCase(username);
		if(userOpt.isEmpty())
			return "Check username";
		User user = (User)userOpt.get();
		if(!password.equals(user.getPassword()))
			return "check password";
		this.user = user;
		return "user loggin successfull";
	}
	
	
	//find user
	public User getUserById(Long userid)
	{
		return userRepository.findById(userid).orElseThrow(
				()-> new ResourceNotFoundException("User is not available with id: "+userid)
				);
	}
	
	//update users name
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
	
	//get list of products
	public List<Product> getProductlist()
	{
		return productRepository.findAll();
	}
	
	//search product by id
	public Product getProductById(Long productid)
	{
		return productRepository.findById(productid).orElseThrow(
				()->new ResourceNotFoundException("product is not available with id: "+productid)
				);
	}
	
	//search product by name
	public Product getProductByName(String productName)
	{
		return productRepository.findByNameIgnoreCase(productName).orElseThrow(
				()->new ResourceNotFoundException("product is not available with Name: "+productName)
				);
	}
	
	//add to cart
		public String addToCart(String productName, int quantity)
		{
			//check user loggedin or not
			if(user==null)
				return "login first";
			
			//check product and quantites available or not
			Optional productOpt = productRepository.findByNameIgnoreCase(productName);
			if(productOpt.isEmpty())
				return "Product is not available now";
			Product product = (Product)productOpt.get();
			if(quantity > product.getQuantity())
				return "Only "+product.getQuantity()+" quantities are available";
			
			// check if ited already added to users cart
			Optional cartOpt = cartRepository.findByProductId(product.getId());
			if(cartOpt.isEmpty())
			{
				Cart cart = new Cart();
				cart.setProduct(product);
				cart.setUser(this.user);
				cart.setQuantity(quantity);
				
				cart.setDate(LocalDateTime.now());
				cartRepository.save(cart);
			}
			Cart cart =(Cart) cartOpt.get();
			cart.setQuantity(quantity+cart.getQuantity());
			
			product.setQuantity(product.getQuantity()-quantity);
			productRepository.save(product);
			return "Item Added to cart";
		}
		
		public List<String> getCart()
		{
			List<String> cartList = new ArrayList<>();
			//check whether user loggedin or not
			if(user == null)
			{
				cartList.add("Login first");
			}
			
			
			//check cart is empty or not				
			
		
			List<Cart> userCart = cartRepository.findAllByUserId(user.getId());
			if(userCart.isEmpty())
				cartList.add("No cart entry found");
			//return all cart entry
			else
			for(Cart cart: userCart)
			{
				cartList.add(cart.toString());
			}
			return  cartList;
			
		}
	
	public Double getCartAmount()
	{
		Double amount =0.0;
		if(user == null)
		{
			return amount;
		}
		List<Cart> cartOpt = cartRepository.findAllByUserId(user.getId());
		if(cartOpt.isEmpty())
			return amount;
		for(Cart cart: cartOpt)
		{
			amount += cart.getProduct().getPrice()*cart.getQuantity();
		}
		return amount;
	}
	
	@Transactional
	public String checkoutCart()
	{
		Double amount = this.getCartAmount();
		if (amount==0.0)
			return "Cart is empty";
		if(amount > user.getBalance())
			return "you have insufficient balance";
		user.setBalance(user.getBalance()-amount);
		userRepository.save(user);
		cartRepository.deleteAllByUserId(user.getId());
		return "Cart Items will be delevered soon";

	}
	
	@Transactional
	public String checkoutItems()
	{
		Double amount = this.getCartAmount();
		if (amount==0.0)
			return "Cart is empty";
		if(amount > user.getBalance())
			return "you have insufficient balance";
		List<Long> productIds = new ArrayList<>();
		List<Cart> cartOpt = cartRepository.findAllByUserId(user.getId());
		System.out.println("Select product id");
		for(Cart cart : cartOpt)
		{
			System.out.println(cart.toString());
			System.out.println("Do you want to delete this item if yes enter quantity (enter 0 for not removal)");
			int inputQuantity = scanner.nextInt();
			if(inputQuantity > 0 && inputQuantity < cart.getQuantity())
			{
				amount +=cart.getProduct().getPrice()*inputQuantity;
				productIds.add(cart.getProduct().getId());
				cart.setQuantity(cart.getQuantity()-inputQuantity);
			}
			if(inputQuantity == cart.getQuantity())
				{
				productIds.add(cart.getProduct().getId());
				cartRepository.delete(cart);
				
				}
			
		}
		user.setBalance(user.getBalance()-amount);
		userRepository.save(user);
		
		return "Cart Items will be delevered soon"+productIds;

	}
	
	public Double getBalance()
	{		
		if(user!=null)
			return userRepository.findById(user.getId()).get().getBalance();
		return 0.0;
	}
	
	public String addBalance(Double amt)
	{
		if(user==null)
			return "Login first";
		userRepository.findById(user.getId()).get().setBalance(amt+user.getBalance());
		return amt+" added to balance";
	}
}
