package com.flipkart.shoppingkart.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.flipkart.shoppingkart.entity.Product;
import com.flipkart.shoppingkart.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	private Product product;
	
	public List<Product> getProducts()
	{
		return productRepository.findAll();
	}
	
	public Product getProduct( Long productId)
	{
		return productRepository.findById(productId).orElseThrow();
	}
	public Product getProductByName(String productName)
	{
		return productRepository.findByNameIgnoreCase(productName).orElseThrow();
	}
	
	public Product addProduct(Product product)
	{
		Optional<Product> optProduct = productRepository.findByNameIgnoreCase(product.getName());
		if(optProduct.isEmpty())
			return productRepository.save(product);
		optProduct.get().setQuantity(product.getQuantity()+optProduct.get().getQuantity());
		return optProduct.get();
	}
	
	public String deleteProduct(Long productId)
	
	{
		if(productRepository.existsById(productId))
		{
			productRepository.deleteById(productId);
			return "product entry deleted";
		}
		return "product id does not exist";
	}
}
