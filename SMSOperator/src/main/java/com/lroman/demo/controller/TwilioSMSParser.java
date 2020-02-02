package com.lroman.demo.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lroman.demo.model.CasinoStreamingServiceInitializer;
import com.lroman.demo.model.TwilioConfig;
import com.lroman.service.EmployeeDriver;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import io.grpc.casinoserviceapi.Employee;
import io.grpc.stub.StreamObserver;


@Component
public class TwilioSMSParser implements  EmployeeDriver{

	@Autowired
	private CasinoStreamingServiceInitializer casinoStreamingService;
	@Autowired
	private TwilioConfig twilio;
	/**
	 * 
	 * @param sms
	 * @return
	 */
	public String getSenderNumber(String sms) {
		String[] tokens = sms.split("=");
		ArrayList<String> number = Stream.of(tokens)
				.filter(str->str.startsWith("%2B"))
				.map(str->str.substring(3, 14))
				.collect(Collectors.toCollection(ArrayList::new));
		return number.get(1);
	}

	/**
	 * 
	 * @param sms
	 * @return
	 */
	public String getSMSRequest(String sms) {
		String[] tokens = sms.split("=");
		ArrayList<String> textRequest = Stream.of(tokens)
				.filter(str->str.startsWith("sick")||str.startsWith("late"))
				.map(str->str.substring(0, 4))
				.collect(Collectors.toCollection(ArrayList::new));
		return textRequest.get(0);
	}

	@Override
	public Employee findReplacement(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String,Integer> selectEmployee(String phoneNumber) {
		// TODO SELECT NAME, ID FROM TABLEGAME WHERE PHONE==phoneNumber
		Map<String,Integer> employee = new LinkedHashMap<>();
		employee.put("Leonardo", 1);
		return employee;
	}

	@Override
	public String selectEmployeeID(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendWorkerRequest(Employee worker) {
		// TODO Auto-generated method stub
		System.out.println("Process for "+worker.getEName()+" successful");

		casinoStreamingService.getStub().processEmployeeRequest(new StreamObserver<Employee>() {

			@Override
			public void onNext(Employee employee) {
				System.out.println(employee.getRequest());
				// Text employee's confirmation
				Message.creator(
						new PhoneNumber(worker.getEPhone()),
						new PhoneNumber(twilio.getTwilioNumber()),
						worker.getEName()+" your request has been processed thanks.\nConfirmation number 1234321" )
				.create();

			}

			@Override
			public void onError(Throwable arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onCompleted() {
				// TODO Auto-generated method stub

			}
		}).onNext(worker);

	}
}
