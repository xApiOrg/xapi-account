package com.santander.xapi.account.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service("accountService")
public class AccountServiceImpl implements AccountService {
	@Autowired 
	private AccountConfig accountConfig;
	
	@Autowired @Qualifier("restTemplateBean")
	private RestTemplate restTemplate;

	@SuppressWarnings("unchecked")
	@Override
//	@HystrixCommand(fallbackMethod="getAllPayableAccountsFallback")
	public Set<Object> getAllPayableAccounts(Long userId) {
		String uri = accountConfig.getProtocol() + accountConfig.getDomain() + accountConfig.getPath();
		Set<Object> response = restTemplate.getForObject( uri + userId, Set.class );
		return response;
	}
	
//	@HystrixCommand
//	private Set<Object> getAllPayableAccountsFallback(Long userId) {
//		return new HashSet<>();
//	}	

	@Override
//	@HystrixCommand(fallbackMethod="getUserAccountByIdFallback")
	public Object getUserAccountById(Long userId, Long id) {
		String uri = accountConfig.getProtocol() + accountConfig.getDomain() + accountConfig.getPath();
		Object response = restTemplate.getForObject( uri + userId + "/" + id, Object.class );
		return response;
	}
	
//	@HystrixCommand
//	private Object getUserAccountByIdFallback(Long userId, Long id) {
//		return new Object();
//	}	
	
	@Primary
	@Bean(name="restTemplateBean")
	private RestTemplate restTemplate(){
		return new RestTemplateBuilder().build();
	}

}
