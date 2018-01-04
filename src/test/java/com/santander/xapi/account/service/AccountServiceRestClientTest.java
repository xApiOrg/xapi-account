package com.santander.xapi.account.service;

import static org.junit.Assert.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.RequestMatcher.*;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@RestClientTest(components = {AccountService.class, AccountConfig.class})
public class AccountServiceRestClientTest {
	@Autowired private AccountConfig accountConfig;
	@Autowired private AccountService accountService;
	@Autowired private RestTemplate template;
	@Autowired private ObjectMapper objectMapper;
	
	private MockRestServiceServer server;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		// Avoids IllegalStateException, MockRestServiceServer not bound to RestTemplate
		server = MockRestServiceServer.createServer(template); 
		
		this.server.expect( requestTo("/account/1/1") ).andExpect( method( HttpMethod.GET ) )
			.andRespond( withSuccess( 
					objectMapper.writeValueAsString( 
							AccountServiceTest.USER_ACCOUNTS.get( 1L ).get( 1L ) ), 
							MediaType.APPLICATION_JSON) );
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testGetAllPayableAccounts() {
//		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testGetUserAccountById() {
//		accountService.getUserAccountById( 1L, 1L ); // IllegalStateException: No Scope registered for scope name 'refresh'
	}

}
// create your own scope
// http://slackspace.de/articles/test-request-scoped-beans-with-spring/ 