package com.lroman.demo.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties(prefix="casino")
public class CasinoStreamingServiceConfig {

	private String localHost;
	private int port;

	public CasinoStreamingServiceConfig() {}

	/**
	 * @return the localHost
	 */
	public String getLocalHost() {
		return localHost;
	}

	/**
	 * @param localHost the localHost to set
	 */
	public void setLocalHost(String localHost) {
		this.localHost = localHost;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}
}
