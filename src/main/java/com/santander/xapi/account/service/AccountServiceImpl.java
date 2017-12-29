package com.santander.xapi.account.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service("accountService")
public class AccountServiceImpl implements AccountService {
	@Autowired 
	private AccountConfig accountConfig;
	
	@Autowired @Qualifier("restTemplateBean")
	private RestTemplate restTemplate;

	@SuppressWarnings("unchecked")
	@Override
	public Set<Object> getAllPayableAccounts(Long userId) {
		String uri = accountConfig.getProtocol() + accountConfig.getDomain() + accountConfig.getPath();
		Set<Object> response = restTemplate.getForObject( uri + userId, Set.class );
		return response;
	}

	@Override
	public Object getUserAccountById(Long userId, Long id) {
		String uri = accountConfig.getProtocol() + accountConfig.getDomain() + accountConfig.getPath();
		Object response = restTemplate.getForObject( uri + userId + "/" + id, Object.class );
		return response;
	}
	
	@Primary
	@Bean(name="restTemplateBean")
	private RestTemplate restTemplate(){
		return new RestTemplateBuilder().build();
	}

}
