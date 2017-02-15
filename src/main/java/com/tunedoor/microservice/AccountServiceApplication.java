package com.tunedoor.microservice;

import java.util.logging.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 
 * @author Mohamed Saeed
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
public class AccountServiceApplication {

	private static Logger logger = Logger.getLogger(AccountServiceApplication.class.getName());

	public static void main(String[] args) {
		System.setProperty("spring.config.name", "account-server");
		logger.info("Change Application Configuration to use account-server.yml");
		SpringApplication.run(AccountServiceApplication.class, args);
	}
}
