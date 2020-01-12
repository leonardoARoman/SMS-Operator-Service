package com.lroman.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.lroman.demo.model.SmsSender;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@SpringBootApplication
public class SmshotlineApplication {
	private static final String ACCOUNT_SID = System.getenv("ACCOUNT_SID");
	private static final String AUTH_TOKEN = System.getenv("AUTH_TOKEN");
	public static void main(String[] args) {
		SpringApplication.run(SmshotlineApplication.class, args);
		
		SmsSender sender = new SmsSender();
		sender.setAccountSid(ACCOUNT_SID);
		sender.setAuthToken(AUTH_TOKEN);
		sender.setTwilioNumber(new PhoneNumber("MY_NUMBER"));
		
		Twilio.init(sender.getAccountSid(), sender.getAuthToken());
		
		Message mssg = Message
				.creator(new PhoneNumber("THEIR_NUMBER"), sender.getTwilioNumber(), "Hello, World!")
				.create();
		System.out.println(mssg.getSid());
	}

}
