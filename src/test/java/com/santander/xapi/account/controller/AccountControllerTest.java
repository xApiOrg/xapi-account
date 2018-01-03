package com.santander.xapi.account.controller;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

//@JsonTest - for testing the JSON marshalling and unmarshalling
//@DataJpaTest - for testing the repository layer
//@RestClientTests - for testing REST clients
//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT) - http random port test

// https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html

@RunWith(SpringRunner.class)
@SpringBootTest // http://www.baeldung.com/spring-boot-testing
@AutoConfigureMockMvc
public class AccountControllerTest {
	private MockMvc mockMvc;
	
	@Autowired
    private WebApplicationContext wac;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup( wac ).build();
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.santander.xapi.account.controller.AccountController#getAllUserPaymentAccounts(java.lang.Long)}.
	 */
	@Test
	public final void testGetAllUserPaymentAccounts()  throws Exception{
		ResultActions resultActions = this.mockMvc.perform( get("/account/1") )
			.andDo( print() )
//			.andExpect( status().isOk() )
//			.andExpect(content().string( containsString("Hello World") ) )
			;
		
		resultActions.toString();
	}

	/**
	 * Test method for {@link com.santander.xapi.account.controller.AccountController#getUserAccountById(java.lang.Long, java.lang.Long)}.
	 */
	@Test
	public final void testGetUserAccountById() {
//		fail("Not yet implemented"); // TODO
	}

}
