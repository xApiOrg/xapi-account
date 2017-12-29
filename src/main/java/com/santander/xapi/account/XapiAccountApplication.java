package com.santander.xapi.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@SpringBootApplication
@EnableAutoConfiguration
@EnableCircuitBreaker
@RefreshScope
public class XapiAccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(XapiAccountApplication.class, args);
	}
}
