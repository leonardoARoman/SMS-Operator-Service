package com.lroman.demo.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.twilio.Twilio;

@Configuration
public class TwilioInitializer {
	
	@Autowired
	public TwilioInitializer(TwilioConfig config) {
		Twilio.init(config.getAccountSid(), config.getAuthToken());
		System.out.println("Twilio has been registered");
	}
}
