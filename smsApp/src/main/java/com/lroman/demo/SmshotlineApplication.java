package com.lroman.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.lroman.demo.model.SmsSender;

//import com.twilio.Twilio;
//import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.messaging.Body;
import com.twilio.twiml.messaging.Message;

import static spark.Spark.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class SmshotlineApplication {
	private static final String ACCOUNT_SID = System.getenv("ACCOUNT_SID");
	private static final String AUTH_TOKEN = System.getenv("AUTH_TOKEN");
	public static void main(String[] args) {
		SpringApplication.run(SmshotlineApplication.class, args);

		SmsSender sender = new SmsSender();
		sender.setAccountSid(ACCOUNT_SID);
		sender.setAuthToken(AUTH_TOKEN);
		sender.setTwilioNumber(new PhoneNumber("NUMBER"));

		
		get("/",(req,res)->{
			// you may use this to replay back 
			//	    Twilio.init(sender.getAccountSid(), sender.getAuthToken());
			//		Message mssg = Message
			//				.creator(new PhoneNumber("MY_NUMBER"), sender.getTwilioNumber(), "Hello, World!")
			//				.create();
			//		System.out.println(mssg.getSid());
			return "the response message";
		});
		
		post("/sms",(req,res)->{

			// Get the user's information (User is the one sending text messages to our server)
			System.out.println("\nThis is the request "+req.body());
			String[] str1 = req.body().split("=");

			Collection<String> str2 = Stream.of(str1)
					.filter(s->s.startsWith("%2B"))
					.map(s->s.substring(3, 14))
					.collect(Collectors.toCollection(ArrayList::new));
			
			Stream.of(str2).forEach(System.out::println);

			res.type("application/xml");
			Body body = new Body
					.Builder("Confirmation message from Scheduling. This is an automated msm message dont respond")
					.build();
			Message mssg = new Message
					.Builder()
					.body(body)
					.build();
			return new MessagingResponse
					.Builder()
					.message(mssg)
					.build()
					.toXml();
		});

	}

}
