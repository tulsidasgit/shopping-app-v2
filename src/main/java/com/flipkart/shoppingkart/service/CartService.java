package com.flipkart.shoppingkart.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.flipkart.shoppingkart.entity.Cart;
import com.flipkart.shoppingkart.entity.Product;
import com.flipkart.shoppingkart.entity.User;
import com.flipkart.shoppingkart.repository.CartRepository;
import com.flipkart.shoppingkart.repository.ProductRepository;
import com.flipkart.shoppingkart.repository.UserRepository;

@Service
public class CartService {
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	@Qualifier("smsNotification")
	private NotificationService smsNotification;
	
	@Autowired
	@Qualifier("emailNotification")
	private NotificationService emailNotification;
	
	public String addToCart(Long userId, Long productId, int quantity, String notifyBy)
	{
		Optional<User> user = userRepository.findById(userId);
		Optional<Product> product = productRepository.findById(productId);
		//checking if user is present in database or not
		if(user.isEmpty())
		{
			return "user not found";
		}
		//checking if product is present or not
		if(product.isEmpty())
		{
			return "proiduct not available";
		}
		
		if(quantity>product.get().getQuantity())
		{
			return "Only "+product.get().getQuantity()+" quantities are available";
		}
		product.get().setQuantity(product.get().getQuantity() - quantity);
		
		// if userid and product id already present in cart then only update quantity 
		List<Cart> productCartList = cartRepository.findByProductId(productId);
		for(Cart cart: productCartList)
		{
			if(cart.getUser().getId().equals(userId))
			{
				cart.setQuantity(cart.getQuantity()+quantity);
				cartRepository.save(cart);
				
				NotificationService notifier = notifyBy.equalsIgnoreCase("sms")?smsNotification:emailNotification;
				String msg = "product: "+product.get().getName()+" added to cart";
				notifier.sendNotification(msg, user.get().getName());
				
				return "product added to card and notification sent by"+notifyBy;
			}
		}
		// else add new entry in cart
		Cart cart = new Cart();
		cart.setUser(user.get());
		cart.setProduct(product.get());
		cart.setDate(LocalDateTime.now());
		cart.setQuantity(quantity);
		
		cartRepository.save(cart);
		
		NotificationService notifier = notifyBy.equalsIgnoreCase("sms")?smsNotification:emailNotification;
		String msg = "product: "+product.get().getName()+" added to cart";
		notifier.sendNotification(msg, user.get().getName());
		
		return "product added to card and notification sent by"+notifyBy;
		
	}
	
	public List<Cart> getAll()
	{
		return cartRepository.findAll();
	}
	
	public List<Cart> getByUserid(Long userid)
	{
//		List<Cart>userCart = new ArrayList<>();
//		for(Cart cart:cartRepository.findAll())
//		{
//			if(cart.getUser().getId()==userid)
//				userCart.add(cart);			
//		}
//		return userCart;
			//OR
			//using stream
		return  cartRepository.findAll().stream().filter(
								cart->userid.equals(cart.getUser().getId()))
								.collect(Collectors.toList());
			// OR modify cartRepository check getbyproductid
	}
	
	public List<Cart> getByProductid(Long productid)
	{
		return  cartRepository.findByProductId(productid);
	}
}
	

