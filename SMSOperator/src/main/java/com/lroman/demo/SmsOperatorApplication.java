package com.lroman.demo;
/**
 * @author leoroman
 * 
 * SMS operator is a micro service to handle inbounds/outbound text messages via the Twilio API. 
 * The software process the inbound SMS and delivers the user's request to the Pencil software,
 * then sends back an SMS confirmation request to the user.
 */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SmsOperatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmsOperatorApplication.class, args);
	}

}
