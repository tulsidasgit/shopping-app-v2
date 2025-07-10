package com.flipkart.shoppingkart.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.flipkart.shoppingkart.entity.Product;
import com.flipkart.shoppingkart.repository.ProductRepository;
import com.flipkart.shoppingkart.service.ProductService;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
	
	@InjectMocks
	private ProductService productService;
	
	@Mock
	private ProductRepository productRepository;
	
	@Test
	void getProducts_EmptyList()
	{
		List<Product> productlist = List.of();
		when(productRepository.findAll()).thenReturn(productlist);
		// check size
		assertEquals(0,productService.getProducts().size());
		// check call for product repository.findAll()
		verify(productRepository).findAll();
	}
	
	@Test
	void getProduct_NotFound()
	{
		when(productRepository.findById(1L)).thenReturn(Optional.empty());
		// check whether exceeption thrown or not
		assertThrows(NoSuchElementException.class,()->{
			productService.getProduct(1L);
		});
		//verify there is call for productRepository.findById()
		verify(productRepository).findById(1L);	
	}
	
	@Test
	void addProduct_TestName()
	{
		Product product = new Product();
		product.setId(2L);
		product.setName("Tea");
		when(productRepository.save(product)).thenReturn(product);
		assertEquals("Tea",productService.addProduct(product).getName());
		verify(productRepository).save(product);
	}

	
}
