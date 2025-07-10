package com.flipkart.shoppingkart.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flipkart.shoppingkart.entity.Cart;
import com.flipkart.shoppingkart.entity.Product;
import com.flipkart.shoppingkart.entity.User;
import com.flipkart.shoppingkart.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	@PostMapping("/add")
	public String addToCart(@RequestParam Long userid,
							@RequestParam Long productid,
							@RequestParam int quantity,
							@RequestParam(defaultValue = "sms") String notifyby )
	{
		 return cartService.addToCart(userid, productid, quantity, notifyby);
		//return "product added to card and notification sent by"+notifyby;
	}
	
	@GetMapping("/all")
	public List<Cart> getAll()
	{
		return cartService.getAll();
	}
	
	@GetMapping("/userid")
	public List<Cart> getByUserid(@RequestParam Long userid)
	{
		return cartService.getByUserid(userid);
	}
	@GetMapping("/userid/requireddata")
	public List<String> getByUserid2(@RequestParam Long userid)
	{
		List<Cart> userCart =  cartService.getByUserid(userid);
		List<String>data = new ArrayList<>();
		int i=1;
		for(Cart cart:userCart)
		{
			String msg = i++ +" Product Name: "+cart.getProduct().getName()+
							  " Cost: "+cart.getProduct().getPrice()+
							  " Quantity: "+cart.getQuantity();
			data.add(msg);
		}
		return data;
	}
	
	@GetMapping("/productid")
	public List<String> getByProductid(@RequestParam Long productid)
	{
		List<Cart>productCart =  cartService.getByProductid(productid);
		List<String>data = new ArrayList<>();
		int i=1;
		for(Cart cart:productCart)
		{
			String msg = i++ +" Product Name: "+cart.getProduct().getName()+
							  " Cost: "+cart.getProduct().getPrice()+
							  " Quantity: "+cart.getQuantity()+
							  " User id "+cart.getUser().getId()+
							  " User Name: "+cart.getUser().getName();
								
			data.add(msg);
		}
		return data;
	}
	
	
}
