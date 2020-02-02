package com.lroman.demo.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import io.grpc.ManagedChannelBuilder;
import io.grpc.casinoserviceapi.CasinoNetworkServiceGrpc;

@Configuration
public class CasinoStreamingServiceInitializer {
		
	private final CasinoNetworkServiceGrpc.CasinoNetworkServiceStub stub;
	private static int instanceCount = 0;
	
	@Autowired
	CasinoStreamingServiceInitializer(CasinoStreamingServiceConfig config){
		stub = CasinoNetworkServiceGrpc
				.newStub(ManagedChannelBuilder
						.forAddress(config.getLocalHost(), config.getPort())
						.usePlaintext()
						.build());
		System.out.println("CasinoStreamingServiceInitializer "+(++instanceCount)+" has been connected");
	}

	/**
	 * @return the stub
	 */
	public CasinoNetworkServiceGrpc.CasinoNetworkServiceStub getStub() {
		return stub;
	}
}
