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

/*
 * 
1. Create the configuration server application 
	- locally https://start.spring.io/
	- remotely 
		https://console.run.pivotal.io/organizations/bd04ca83-2a38-4837-87b3-3f6ce2dbbc07/spaces/33d61af8-dd1b-4445-82f1-1c40230d95d7/services
		
		
2. Application properties
	spring.application.name=xapi-account-config-server
	server.port=8888
	spring.cloud.config.server.git.uri=file://${user.home}/config-service-example-config
									or
	spring.cloud.config.server.git.uri=https://github.com/xApiOrg/config-repo/blob/master/xapi-account.yml
	
	https://docs.pivotal.io/spring-cloud-services/1-3/common/config-server/
	
	cf update-service xapi-account-config-server -c '{ "git": { "uri": "https://github.com/xApiOrg/config-repo", "label": "master" } }'

3. 

 * 
 * 
*/
