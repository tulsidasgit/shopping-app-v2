package com.flipkart.shoppingkart.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.Getter;

@Data		// this provide setter getter and reqd constructors
@Entity								// Map this class to the database table
public class User {
	@Id								// make id as primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)		// this will generate values for id automatically
	private Long id;
	private String name;
	private String username;
	private String password;
	private Double balance=50000.0;
	
	public Double getBalance()
	{
		return balance;
	}
	public void setBalance(Double amt)
	{
		this.balance = amt;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	// this represent oneto one relationship with cart
	//if we save/delete user then its cart will also be saved/deleted
//	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
//	private Cart cart;
	

}
