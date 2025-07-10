package com.flipkart.shoppingkart.service;

import org.springframework.stereotype.Service;

@Service("emailNotification")
public class EmailNotification implements NotificationService {
	
	public void sendNotification(String msg, String to)
	{
		System.out.println("Email sent with msg: "+msg+" to the user: "+to);
	}

}
