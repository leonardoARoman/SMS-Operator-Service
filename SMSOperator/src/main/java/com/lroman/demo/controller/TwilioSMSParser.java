package com.lroman.demo.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import io.grpc.ManagedChannelBuilder;
import io.grpc.casinoserviceapi.CasinoNetworkServiceGrpc;
import io.grpc.casinoserviceapi.Employee;
import io.grpc.casinoserviceapi.Employee.ABSENCE;
import io.grpc.casinoserviceapi.Employee.SHIFT;
import io.grpc.stub.StreamObserver;
import javafx.application.Platform;

@Component
public class TwilioSMSParser implements DataBaseHandler {

	public String getSenderNumber(String sms) {
		String[] tokens = sms.split("=");
		ArrayList<String> number = Stream.of(tokens)
				.filter(str->str.startsWith("%2B"))
				.map(str->str.substring(3, 14))
				.collect(Collectors.toCollection(ArrayList::new));
		return number.get(1);
	}

	public String getSMSRequest(String sms) {
		String[] tokens = sms.split("=");
		ArrayList<String> textRequest = Stream.of(tokens)
				.filter(str->str.startsWith("sick")||str.startsWith("late"))
				.map(str->str.substring(0, 4))
				.collect(Collectors.toCollection(ArrayList::new));
		return textRequest.get(0);
	}
	
	@Override
	public Employee findEmployee(String phoneNumber, String request) {
		// TODO Auto-generated method stub
		// TODO connect to data base to find worker given a phone number
		Employee newEmployee = null;
		if(phoneNumber.equals("17323742773")) {
			if(request.equals("sick")) {
				newEmployee = Employee.newBuilder()
						.setEName("Leonardo")
						.setEId(1)
						.setEShift(SHIFT.DAY)
						.setEStatus(ABSENCE.SICK)
						.build();
			}else if(request.equals("late")){
				newEmployee = Employee.newBuilder()
						.setEName("Leonardo")
						.setEId(1)
						.setEShift(SHIFT.DAY)
						.setEStatus(ABSENCE.LATE)
						.build();
			}else {
				newEmployee = Employee.newBuilder().build();
			}
		}
		return newEmployee;
	}

	@Override
	public void processWorkerRequest(Employee worker) {
		// TODO Auto-generated method stub
		System.out.println("processWorkerRequest was called!");
		// TODO Auto-generated method stub
		Employee employee = Employee.newBuilder()
				.setEName("Leonardo")
				.setEId(1)
				.setEShift(SHIFT.DAY)
				.setEStatus(ABSENCE.SICK)
				.build();
		String str = "";
		try {
			str = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		CasinoNetworkServiceGrpc.CasinoNetworkServiceStub newStub = CasinoNetworkServiceGrpc
				.newStub(ManagedChannelBuilder
						.forAddress(str, 8081)
						.usePlaintext()
						.build());

		newStub.processEmployeeRequest(new StreamObserver<Employee>() {

			@Override
			public void onNext(Employee arg0) {
				// TODO Auto-generated method stub
				Platform.runLater(()->{
					System.out.println("a called back!");
				});
			}

			@Override
			public void onError(Throwable arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onCompleted() {
				// TODO Auto-generated method stub

			}
		}).onNext(employee);
	}
}
