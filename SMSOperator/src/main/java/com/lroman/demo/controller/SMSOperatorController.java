package com.lroman.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.messaging.Body;
import com.twilio.twiml.messaging.Message;

import io.grpc.casinoserviceapi.Employee;

@RestController
public class SMSOperatorController {

	@Autowired
	TwilioSMSParser parser;
	
	/**
	 * 
	 * @param postBody
	 * @return
	 */
	@RequestMapping(value="/postTextMessage",method=RequestMethod.POST)
	public String postTextMessage(@RequestBody String postBody) {
		
		String phoneNumber = parser.getSenderNumber(postBody);
		String smsRequest = parser.getSMSRequest(postBody);
		
		new Thread(()-> {
			Employee employee = parser.findEmployee(phoneNumber,smsRequest);
			parser.processWorkerRequest(employee);
		}).start();
		
		Body body = new Body
				.Builder("A "+smsRequest+" message has been processed for "+phoneNumber+"\nThanks!")
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
}
