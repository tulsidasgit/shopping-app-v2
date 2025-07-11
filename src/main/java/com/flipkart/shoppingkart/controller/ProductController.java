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

import com.flipkart.shoppingkart.entity.Product;
import com.flipkart.shoppingkart.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@GetMapping("/all")
	public List<Product> getProducts()
	{
		return productService.getProducts();
	}
	
	@GetMapping("/id")
	public Product getProduct(@RequestParam Long productid)
	{
		return productService.getProduct(productid);
	}
	
	@GetMapping("/name")
	public Product getProduct(@RequestParam String name)
	{
		return productService.getProductByName(name);
	}
	
	@PostMapping("/add")
	public Product addProduct(@RequestBody Product product)
	{
		return productService.addProduct(product);
	}
	
	@PutMapping("/update")
	public Product updateProduct(@RequestBody Product product)
	{
		return productService.addProduct(product);
	}
	
	@DeleteMapping("/delete")
	public String deleteProduct(@RequestParam Long productId)
	{
		return productService.deleteProduct(productId);
	}
}
