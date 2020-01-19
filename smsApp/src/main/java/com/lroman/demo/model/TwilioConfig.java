package com.lroman.demo.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties(prefix="twilio")
public class TwilioConfig {
	private String accountSid;
	private String authToken;
	private String twilioNumber;
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
	public String getTwilioNumber() {
		return twilioNumber;
	}
	/**
	 * @param twilioNumber the twilioNumber to set
	 */
	public void setTwilioNumber(String twilioNumber) {
		this.twilioNumber = twilioNumber;
	}
	
}
