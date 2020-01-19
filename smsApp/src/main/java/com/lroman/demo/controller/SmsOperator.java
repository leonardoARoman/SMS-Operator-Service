package com.lroman.demo.controller;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.messaging.Body;
import com.twilio.twiml.messaging.Message;

@RestController
public class SmsOperator {

	@RequestMapping(value="/postTextMessage",method=RequestMethod.POST)
	public String postTextMessage(@RequestBody String postBody) {
		// Get the user's information (User is the one sending text messages to our server)
		String[] str = getTextQuery(postBody);
		String message=str[1];
		String number=str[0];
		// TODO: process the request (was a valid message? what is the mssg request? sick,late?
		Body body = new Body
				.Builder("A "+message+" message has been processed for "+number+"\nThanks!")
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
	}
	
	private String[] getTextQuery(String postBody) {
		String[] strings = postBody.split("=");
		//Stream.of(strings).forEach(System.out::println);
		ArrayList<String> number = Stream.of(strings)
				.filter(str->str.startsWith("%2B")||str.startsWith("late")||str.startsWith("sick"))
				.map(str->str.substring(3, 14))
				.collect(Collectors.toCollection(ArrayList::new));

		ArrayList<String> textRequest = Stream.of(strings)
				.filter(str->str.startsWith("sick")||str.startsWith("late"))
				.map(str->str.substring(0, 4))
				.collect(Collectors.toCollection(ArrayList::new));
		
		//Stream.of(textRequest).forEach(System.out::println);
		String[] numberAndQuery = new String[2];
		numberAndQuery[0] = number.get(1);
		numberAndQuery[1] = textRequest.get(0);
		return numberAndQuery;
	}
}
