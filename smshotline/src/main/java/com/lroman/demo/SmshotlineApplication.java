package com.lroman.demo;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import workerAPI.ServiceStub;


@SpringBootApplication
public class SmshotlineApplication {

	public static void main(String[] args) throws IOException, InterruptedException {
		SpringApplication.run(SmshotlineApplication.class, args);
		ServiceStub.starServer();
	}
}
