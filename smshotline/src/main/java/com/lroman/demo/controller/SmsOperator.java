package com.lroman.demo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.messaging.Body;
import com.twilio.twiml.messaging.Message;

import io.grpc.workerapi.Employee;

@RestController
public class SmsOperator {

	@Autowired
	DataBaseHandler handler;
	
	/**
	 * 
	 * @param postBody
	 * @return
	 */
	@RequestMapping(value="/postTextMessage",method=RequestMethod.POST)
	public String postTextMessage(@RequestBody String postBody) {
		
		String[] str = getTextQuery(postBody); // Get the user's information (User is the one sending text messages to our server)
		String number=str[0];
		String request=str[1];
		
		Body body = new Body
				.Builder("A "+request+" message has been processed for "+number+"\nThanks!")
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
	
	/**
	 * 
	 * @param postBody to process the request (was a valid message? what is the message request? sick,late?
	 * @return phone number and text message requested
	 */
	private String[] getTextQuery(String postBody) {
		String[] strings = postBody.split("=");
		//Stream.of(strings).forEach(System.out::println);
		ArrayList<String> number = Stream.of(strings)
				.filter(str->str.startsWith("%2B"))
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
		
		new Thread(()-> {
			Employee employee = (Employee)handler.findWorker(numberAndQuery[0],numberAndQuery[1]);
			handler.processWorkerRequest(employee);
		}).start();
		return numberAndQuery;
	}
}
