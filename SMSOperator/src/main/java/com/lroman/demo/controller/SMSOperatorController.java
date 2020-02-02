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
	private TwilioSMSParser parser;
	@Autowired
	private DataBaseHelper query;

	/**
	 * 
	 * @param postBody
	 * @return
	 */
	@RequestMapping(value="/postTextMessage",method=RequestMethod.POST)
	public String postTextMessage(@RequestBody String postBody) {
		// Parse the string and get phone number and request from employee
		String phoneNumber = parser.getSenderNumber(postBody);
		String smsRequest = parser.getSMSRequest(postBody);
		String confirmation = "A "+smsRequest+" message has been processed for "+phoneNumber+"\nThanks!";
		// Process employee's request and do not block
		Employee employee = getEmployee(phoneNumber,smsRequest);
		// Check for valid request
		if(employee.getEId()==0) {
			confirmation = "Request not recognized, are you calling sick or late?";
		}else {
			new Thread(()->query.sendWorkerRequest(employee)).start();
		}
		// Send a received SMS confirmation
		Body body = new Body
				.Builder(confirmation)
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
	 * Find the employee who texted the SMS operator
	 * @param phoneNumber
	 * @param smsRequest
	 * @return
	 */
	private Employee getEmployee(String phoneNumber, String smsRequest) {
		// Find employee by phone number look up
		Map.Entry<String,Integer> entry = query
				.selectEmployee(phoneNumber)
				.entrySet()
				.iterator()
				.next();
		// Store name and ID
		String key = entry.getKey();
		int value = entry.getValue();
		// Build and return an Employee data structured
		if(smsRequest.equals("late")) {
			return  Employee.newBuilder()
					.setEName(key)
					.setEId(value)
					.setEPhone(phoneNumber)
					.setEShift(SHIFT.DAY)
					.setEStatus(ABSENCE.LATE)
					.setRequest(key+" will be "+smsRequest)
					.build();
		}else if(smsRequest.equals("sick")) {
			return Employee.newBuilder()
					.setEName(key)
					.setEId(value)
					.setEPhone(phoneNumber)
					.setEShift(SHIFT.DAY)
					.setEStatus(ABSENCE.SICK)
					.setRequest(key+" will be "+smsRequest)
					.build();
		}
		return Employee.newBuilder().build();
	}
}
