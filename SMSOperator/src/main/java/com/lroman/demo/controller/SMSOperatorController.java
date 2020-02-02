package com.lroman.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.messaging.Body;
import com.twilio.twiml.messaging.Message;

import io.grpc.casinoserviceapi.Employee;
import io.grpc.casinoserviceapi.Employee.ABSENCE;
import io.grpc.casinoserviceapi.Employee.SHIFT;

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

		new Thread(()-> sendRequest(phoneNumber,smsRequest)).start();

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
	
	private void sendRequest(String phoneNumber, String smsRequest) {
		Map.Entry<String,Integer> entry = parser
				.selectEmployee(phoneNumber)
				.entrySet()
				.iterator()
				.next();
		String key = entry.getKey();
		int value = entry.getValue();
		Employee newEmployee = null;

		if(smsRequest.equals("late")) {
			newEmployee = Employee.newBuilder()
					.setEName(key)
					.setEId(value)
					.setEPhone(phoneNumber)
					.setEShift(SHIFT.DAY)
					.setEStatus(ABSENCE.LATE)
					.setRequest(key+" will be "+smsRequest)
					.build();
		}else if(smsRequest.equals("sick")) {
			newEmployee = Employee.newBuilder()
					.setEName(key)
					.setEId(value)
					.setEPhone(phoneNumber)
					.setEShift(SHIFT.DAY)
					.setEStatus(ABSENCE.SICK)
					.setRequest(key+" will be "+smsRequest)
					.build();
		}else {
			newEmployee = Employee.newBuilder().build();
		}
		parser.sendWorkerRequest(newEmployee);
	}
}
