package com.lroman.demo.controller;

public interface SmsService {

	/**
	 * 
	 * @param phoneNumber find person in database given a phone number
	 * @return Object returns a Person object
	 */
	Object findWorker(String phoneNumber);
	
	/**
	 * 
	 * @param worker update workers status (late or absent)
	 */
	void processWorkerRequest(Object worker);
}
