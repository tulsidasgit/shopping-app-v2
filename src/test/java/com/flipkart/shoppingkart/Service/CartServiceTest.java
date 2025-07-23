package com.flipkart.shoppingkart.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.flipkart.shoppingkart.entity.Cart;
import com.flipkart.shoppingkart.entity.Product;
import com.flipkart.shoppingkart.entity.User;
import com.flipkart.shoppingkart.repository.CartRepository;
import com.flipkart.shoppingkart.repository.ProductRepository;
import com.flipkart.shoppingkart.repository.UserRepository;
import com.flipkart.shoppingkart.service.CartService;
import com.flipkart.shoppingkart.service.NotificationService;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @InjectMocks
    private CartService cartService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private NotificationService smsNotification;

    @Mock
    private NotificationService emailNotification;

    @Test
    void addToCart_UserNotFound()
    {
    	//we are telling mockito and setting following scenario
    	//when userRepository.findById(1L) method calls then that time return empty list means no user found
    	when(userRepository.findById(1L)).thenReturn(Optional.empty());
    	String result = cartService.addToCart(1L, 1L, 10, "sms");
    	assertEquals("user not found",result);
    }
    
    @Test
    void addToCart_ProductNotFound()
    {
    	// first set scenario that user is present with id 1
    	User user = new User();
    	user.setId(1L);
    	when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    	//now check for product availability for id 2
    	//setting scenario that product with id 2 not available
    	when(productRepository.findById(2L)).thenReturn(Optional.empty());
    	String result = cartService.addToCart(1L, 2L, 12, "sms");
    	assertEquals("proiduct not available",result);
    	
    }
    
    
    @Test
    void addToCart_ProductAlreadyInCart() {
        User user = new User(); user.setId(1L); user.setName("John");
        Product product = new Product(); product.setId(5L); product.setName("Shoes");

        Cart cart = new Cart();
        cart.setId(10L);
        cart.setUser(user);
        cart.setProduct(product);
        cart.setQuantity(2);

        List<Cart> productCartList = List.of(cart);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(productRepository.findById(5L)).thenReturn(Optional.of(product));
//        when(cartRepository.findByProductId(5L)).thenReturn(productCartList);

        String result = cartService.addToCart(1L, 5L, 3, "sms");

        assertEquals("product added to card and notification sent bysms", result);
        verify(cartRepository).save(any(Cart.class));
        verify(smsNotification).sendNotification("product: Shoes added to cart", "John");
    }

    @Test
    void addToCart_NewProductForUser() {
        User user = new User(); user.setId(1L); user.setName("John");
        Product product = new Product(); product.setId(7L); product.setName("Bag");

        // No cart yet for user-product
        List<Cart> emptyCartList = Collections.emptyList();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(productRepository.findById(7L)).thenReturn(Optional.of(product));
//        when(cartRepository.findByProductId(7L)).thenReturn(emptyCartList);

        String result = cartService.addToCart(1L, 7L, 1, "email");

        assertEquals("product added to card and notification sent byemail", result);
        verify(cartRepository).save(any(Cart.class));
        verify(emailNotification).sendNotification("product: Bag added to cart", "John");
    }
}