package com.lroman.demo.controller;

import io.grpc.casinoserviceapi.Employee;

public interface DataBaseHandler {

	/**
	 * 
	 * @param phoneNumber find person in database given a phone number
	 * @return Object returns a Person object
	 */
	Employee findEmployee(String phoneNumber, String request);

	/**
	 * 
	 * @param worker update workers status (late or absent)
	 */
	void processWorkerRequest(Employee worker);
}
