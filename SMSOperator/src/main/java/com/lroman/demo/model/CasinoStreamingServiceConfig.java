package com.lroman.demo.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import io.grpc.ManagedChannelBuilder;
import io.grpc.casinoserviceapi.CasinoNetworkServiceGrpc;

@Component
@ConfigurationProperties(prefix="casino")
public class CasinoStreamingServiceConfig {

	private String localHost;
	private int port;

	CasinoNetworkServiceGrpc.CasinoNetworkServiceStub newStub;

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

	/**
	 * @return the newStub
	 */
	public CasinoNetworkServiceGrpc.CasinoNetworkServiceStub getNewStub() {
		if(newStub == null) {
			newStub = CasinoNetworkServiceGrpc
					.newStub(ManagedChannelBuilder
							.forAddress(localHost, port)
							.usePlaintext()
							.build());
		}
		return newStub;
	}

	/**
	 * @param newStub the newStub to set
	 */
	public void setNewStub(CasinoNetworkServiceGrpc.CasinoNetworkServiceStub newStub) {
		this.newStub = newStub;
	}
}
