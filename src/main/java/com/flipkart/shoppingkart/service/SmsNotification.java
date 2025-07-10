package com.flipkart.shoppingkart.service;

import org.springframework.stereotype.Service;

@Service("smsNotification")
public class SmsNotification implements NotificationService{
	public void sendNotification(String msg,String to)
	{
		System.out.println("SMS sent with msg: "+msg+" to the user: "+to);
	}
}
