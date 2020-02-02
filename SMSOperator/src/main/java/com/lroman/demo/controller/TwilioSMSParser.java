package com.lroman.demo.controller;

import java.util.ArrayList;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

@Component
public class TwilioSMSParser {

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
}
