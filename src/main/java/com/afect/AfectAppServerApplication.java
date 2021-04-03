package com.afect;


import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AfectAppServerApplication {
	private static final Logger LOGGER = Logger.getLogger(AfectAppServerApplication.class);

	public static void main(String[] args) {
		LOGGER.info("Starting Server...");
		SpringApplication.run(AfectAppServerApplication.class, args);
		
	}

}