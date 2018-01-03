package com.santander.xapi.account.controller;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.santander.xapi.account.service.AccountService;
import com.santander.xapi.account.service.AccountServiceTest;

@WebMvcTest(AccountController.class) //@WebMvcTest - for testing the controller layer
@RunWith(SpringRunner.class)
public class AccountControllerWebMvcTest {	
	@MockBean private AccountService accountService;
	
	@Autowired private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		// https://www.programcreek.com/java-api-examples/index.php?api=org.mockito.BDDMockito
		given( this.accountService.getAllPayableAccounts( 1L ))
			.willReturn( new HashSet<Object>( AccountServiceTest.ACCOUNTS_1.values() ) ); 
		
		given( this.accountService.getAllPayableAccounts( 2L ))
			.willReturn( new HashSet<Object>( AccountServiceTest.ACCOUNTS_2.values() ) ); 
		
		given( this.accountService.getAllPayableAccounts( 3L ))
			.willReturn( null ); 
		
		given( this.accountService.getUserAccountById( 1L, 1L ))
			.willReturn( AccountServiceTest.USER_ACCOUNTS.get( 1L ).get( 1L ) );
		
		given( this.accountService.getUserAccountById( 1L, 2L ))
			.willReturn( AccountServiceTest.USER_ACCOUNTS.get( 1L ).get( 2L ) );
		
		given( this.accountService.getUserAccountById( 2L, 3L ))
			.willReturn( AccountServiceTest.USER_ACCOUNTS.get( 2L ).get( 3L ) ); 
		
		given( this.accountService.getUserAccountById( 2L, 4L ))
			.willReturn( AccountServiceTest.USER_ACCOUNTS.get( 2L ).get( 4L ) ); 		

//		when( this.accountService.getAllPayableAccounts( null ) ).thenReturn( new HashSet<Object>() );
//		when( this.accountService.getAllPayableAccounts( 1L ) ).thenReturn(null); // NB It will override the above given statement
	}
	
	@Test
	public final void testGetAllPayableAccounts(){
		Set<Object> accounts = accountService.getAllPayableAccounts( 1L );
		assertNotNull( accounts );
		assertEquals( new HashSet<Object>( AccountServiceTest.ACCOUNTS_1.values() ), accounts);
		assertTrue( accounts.contains(AccountServiceTest.ACCOUNTS_1.get( 1L )));
		assertTrue( accounts.contains(AccountServiceTest.ACCOUNTS_1.get( 2L )));
		
		accounts = accountService.getAllPayableAccounts( 2L );
		assertNotNull( accounts );
		assertEquals( new HashSet<Object>( AccountServiceTest.ACCOUNTS_2.values() ), accounts);
		assertTrue( accounts.contains(AccountServiceTest.ACCOUNTS_2.get( 3L )));
		assertTrue( accounts.contains(AccountServiceTest.ACCOUNTS_2.get( 4L )));
		
		accounts = accountService.getAllPayableAccounts( 3L );
		assertNull( accounts );
	}

	@Test
	public final void testGetAllUserPaymentAccounts() throws Exception {		
		this.mockMvc.perform( get("/account/1").accept(MediaType.APPLICATION_JSON) )
			.andExpect( content().contentType(MediaType.APPLICATION_JSON_UTF8) )	
			.andExpect( status().is( HttpStatus.I_AM_A_TEAPOT.value() ))
			.andExpect( jsonPath("$", hasSize( 2 )) )
			.andExpect( jsonPath("$[ 0 ].id").exists() )
			.andExpect( jsonPath("$[ 1 ].id").exists() )
			.andExpect( jsonPath("$[ 2 ].id").doesNotExist() )
			.andExpect( jsonPath("$[ 0 ].userId").exists() )
			.andExpect( jsonPath("$[ 1 ].userId").exists() )
			.andExpect( jsonPath("$[ 2 ].userId").doesNotExist() )
			.andExpect( jsonPath("$[ 0 ].id").value( 1 ) )
			.andExpect( jsonPath("$[ 1 ].id").value( 2 ) )
			.andExpect( jsonPath("$[ 0 ].userId").value( 1 ) )
			.andExpect( jsonPath("$[ 1 ].userId").value( 1 ) )
			.andDo( print() );
		
		this.mockMvc.perform( get("/account/2").accept(MediaType.APPLICATION_JSON) )
			.andExpect( content().contentType(MediaType.APPLICATION_JSON_UTF8) )	
			.andExpect( status().is( HttpStatus.I_AM_A_TEAPOT.value() ))
			.andExpect( jsonPath("$", hasSize( 2 )) )
			.andExpect( jsonPath("$[ 0 ].id").exists() )
			.andExpect( jsonPath("$[ 1 ].id").exists() )
			.andExpect( jsonPath("$[ 2 ].id").doesNotExist() )
			.andExpect( jsonPath("$[ 0 ].userId").exists() )
			.andExpect( jsonPath("$[ 1 ].userId").exists() )
			.andExpect( jsonPath("$[ 2 ].userId").doesNotExist() )
			.andExpect( jsonPath("$[ 0 ].id").value( 3 ) )
			.andExpect( jsonPath("$[ 1 ].id").value( 4 ) )
			.andExpect( jsonPath("$[ 0 ].userId").value( 2 ) )
			.andExpect( jsonPath("$[ 1 ].userId").value( 2 ) )
			.andDo( print() );
		
		this.mockMvc.perform( get("/account/3").accept(MediaType.APPLICATION_JSON) )	
			.andExpect( status().is( HttpStatus.I_AM_A_TEAPOT.value() ))			
			.andExpect(jsonPath( "$.id").doesNotExist())			
			.andExpect(jsonPath( "$.userId").doesNotExist())
			.andExpect(jsonPath( "$.keyToNull").doesNotExist())
			.andDo( print() );
	}
	
	@Test
	public final void getUserAccountById(){
		Object account = accountService.getUserAccountById( 1L, 1L );
		assertNotNull( account );
		assertEquals( AccountServiceTest.ACCOUNTS_1.get( 1L ), account);
		assertNotEquals( AccountServiceTest.ACCOUNTS_1.get( 2L ), account);
		
		account = accountService.getUserAccountById( 1L, 2L );
		assertNotNull( account );
		assertEquals( AccountServiceTest.ACCOUNTS_1.get( 2L ), account);
		assertNotEquals( AccountServiceTest.ACCOUNTS_1.get( 1L ), account);
		
		account = accountService.getUserAccountById( 1L, 3L );
		assertNull( account );
		
		account = accountService.getUserAccountById( 2L, 3L );
		assertNotNull( account );
		assertEquals( AccountServiceTest.ACCOUNTS_2.get( 3L ), account);
		assertNotEquals( AccountServiceTest.ACCOUNTS_2.get( 4L ), account);
		
		account = accountService.getUserAccountById( 2L, 4L );
		assertNotNull( account );
		assertEquals( AccountServiceTest.ACCOUNTS_2.get( 4L ), account);
		assertNotEquals( AccountServiceTest.ACCOUNTS_2.get( 3L ), account);
		
		account = accountService.getUserAccountById( 2L, 5L );
		assertNull( account );
	}

	@Test
	public final void testGetUserAccountById() throws Exception {	
		this.mockMvc.perform( get("/account/1/1").accept(MediaType.APPLICATION_JSON) )
		.andExpect( content().contentType(MediaType.APPLICATION_JSON_UTF8) )	
		.andExpect( status().isOk())
		.andExpect( jsonPath("$.id").exists() )
		.andExpect( jsonPath("$.id").value( 1 ) )
		.andExpect( jsonPath("$.userId").exists() )
		.andExpect( jsonPath("$.userId").value( 1 ) ) 
		.andDo( print() );	
		
		this.mockMvc.perform( get("/account/1/2").accept(MediaType.APPLICATION_JSON) )
		.andExpect( content().contentType(MediaType.APPLICATION_JSON_UTF8) )	
		.andExpect( status().isOk())
		.andExpect( jsonPath("$.id").exists() )
		.andExpect( jsonPath("$.id").value( 2 ) )
		.andExpect( jsonPath("$.userId").exists() )
		.andExpect( jsonPath("$.userId").value( 1 ) ) 
		.andDo( print() );
		
		this.mockMvc.perform( get("/account/1/3").accept(MediaType.APPLICATION_JSON) )	
		.andExpect( status().is( HttpStatus.NOT_FOUND.value() ))
		.andExpect( jsonPath("$.id").doesNotExist()) 
		.andExpect( jsonPath("$.userId").doesNotExist() ) 
		.andDo( print() );
		
		this.mockMvc.perform( get("/account/2/3").accept(MediaType.APPLICATION_JSON) )
		.andExpect( content().contentType(MediaType.APPLICATION_JSON_UTF8) )	
		.andExpect( status().isOk())
		.andExpect( jsonPath("$.id").exists() )
		.andExpect( jsonPath("$.id").value( 3 ) )
		.andExpect( jsonPath("$.userId").exists() )
		.andExpect( jsonPath("$.userId").value( 2 ) ) 
		.andDo( print() );
		
		this.mockMvc.perform( get("/account/2/4").accept(MediaType.APPLICATION_JSON) )
		.andExpect( content().contentType(MediaType.APPLICATION_JSON_UTF8) )	
		.andExpect( status().isOk())
		.andExpect( jsonPath("$.id").exists() )
		.andExpect( jsonPath("$.id").value( 4 ) )
		.andExpect( jsonPath("$.userId").exists() )
		.andExpect( jsonPath("$.userId").value( 2 ) ) 
		.andDo( print() );
		
		this.mockMvc.perform( get("/account/2/6").accept(MediaType.APPLICATION_JSON) )	
		.andExpect( status().is( HttpStatus.NOT_FOUND.value() ))
		.andExpect( jsonPath("$.id").doesNotExist()) 
		.andExpect( jsonPath("$.userId").doesNotExist() ) 
		.andDo( print() );
	}

}
