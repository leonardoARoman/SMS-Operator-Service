package com.lroman.demo.model;

import com.twilio.type.PhoneNumber;

public class SmsSender {
	
	private String accountSid;
	private String authToken;
	private PhoneNumber twilioNumber;
	
	public SmsSender() {}

	/**
	 * @return the accountSid
	 */
	public String getAccountSid() {
		return accountSid;
	}

	/**
	 * @param accountSid the accountSid to set
	 */
	public void setAccountSid(String accountSid) {
		this.accountSid = accountSid;
	}

	/**
	 * @return the authToken
	 */
	public String getAuthToken() {
		return authToken;
	}

	/**
	 * @param authToken the authToken to set
	 */
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	/**
	 * @return the twilioNumber
	 */
	public PhoneNumber getTwilioNumber() {
		return twilioNumber;
	}

	/**
	 * @param twilioNumber the twilioNumber to set
	 */
	public void setTwilioNumber(PhoneNumber twilioNumber) {
		this.twilioNumber = twilioNumber;
	}

	@Override
	public String toString() {
		return "SmsSender [accountSid=" + accountSid + ", authToken=" + authToken + ", twilioNumber=" + twilioNumber
				+ "]";
	}
	
}
