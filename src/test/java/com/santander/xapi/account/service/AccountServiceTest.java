package com.santander.xapi.account.service;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
public class AccountServiceTest {
	@Mock private AccountConfig accountConfig; 
	@Mock private RestTemplate restTemplate;
	
	@InjectMocks private AccountServiceImpl accountService;

	//"[{\"id\": 1},{\"id\": 2},{\"id\": 3},{\"id\": 4}]"
	@SuppressWarnings("serial")
	public static final Map<Long, Object> ACCOUNTS_1 =  new HashMap<Long, Object>(){{ 
		put( 1L, new HashMap<Object, Object>(){{ put( "id", "1"); put( "userId", "1"); }}); 
		put( 2L, new HashMap<Object, Object>(){{ put( "id", "2"); put( "userId", "1"); }} ); }};
		
	@SuppressWarnings("serial")
	public static final Map<Long, Object> ACCOUNTS_2 =  new HashMap<Long, Object>() {{ 
		put( 3L, new HashMap<Object, Object>(){{ put( "id", "3"); put( "userId", "2"); }}); 
		put( 4L, new HashMap<Object, Object>(){{ put( "id", "4"); put( "userId", "2"); }}); }};
		
	@SuppressWarnings("serial")
	public static final Map<Long, Map<Long, Object>> USER_ACCOUNTS =  new HashMap<Long, Map<Long, Object>>() {{ 
		put(1L, ACCOUNTS_1); put(2L, ACCOUNTS_2); }};
	
	private static String URI = "http://ec2-52-56-203-3.eu-west-2.compute.amazonaws.com/ipay/account/";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);	
		
		when(accountConfig.getProtocol()).thenReturn("http://");
		when(accountConfig.getDomain()).thenReturn("ec2-52-56-203-3.eu-west-2.compute.amazonaws.com");
		when(accountConfig.getPath()).thenReturn("/ipay/account/");

		when(  restTemplate.getForObject( URI + 1L, Set.class ) )
			.thenReturn( new HashSet<Object>( USER_ACCOUNTS.get( 1L ).values() ));
		when(  restTemplate.getForObject( URI + 2L, Set.class ) )
			.thenReturn( new HashSet<Object>( USER_ACCOUNTS.get( 2L ).values() ));

		when(  restTemplate.getForObject( URI + 1L + "/" + 1L, Object.class ) ).thenReturn( USER_ACCOUNTS.get( 1L ).get( 1L ) );
		when(  restTemplate.getForObject( URI + 1L + "/" + 2L, Object.class ) ).thenReturn( USER_ACCOUNTS.get( 1L ).get( 2L ) );
		
		when(  restTemplate.getForObject( URI + 2L + "/" + 3L, Object.class ) ).thenReturn( USER_ACCOUNTS.get( 2L ).get( 3L ) );
		when(  restTemplate.getForObject( URI + 2L + "/" + 4L, Object.class ) ).thenReturn( USER_ACCOUNTS.get( 2L ).get( 4L ) );		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testUri() {	
		String uri = accountConfig.getProtocol() + accountConfig.getDomain() + accountConfig.getPath();
		assertEquals( URI, uri );
	}

	@Test
	public final void getAllPayableAccounts() {		
		Set<Object> accounts = accountService.getAllPayableAccounts( 1L );
		assertNotNull( accounts );
		assertEquals( new HashSet<Object>( ACCOUNTS_1.values() ), accounts);
		assertTrue( accounts.contains(ACCOUNTS_1.get( 1L )));
		assertTrue( accounts.contains(ACCOUNTS_1.get( 2L )));
		
		accounts = accountService.getAllPayableAccounts( 2L );
		assertNotNull( accounts );
		assertEquals( new HashSet<Object>( ACCOUNTS_2.values() ), accounts);
		assertTrue( accounts.contains(ACCOUNTS_2.get( 3L )));
		assertTrue( accounts.contains(ACCOUNTS_2.get( 4L )));
		
		accounts = accountService.getAllPayableAccounts( 3L );
		assertNull( accounts );
	}

	@Test
	public final void getUserAccountById() {		
		Object account = accountService.getUserAccountById( 1L, 1L );
		assertNotNull( account );
		assertEquals( ACCOUNTS_1.get( 1L ), account);
		assertNotEquals( ACCOUNTS_1.get( 2L ), account);
		
		account = accountService.getUserAccountById( 1L, 2L );
		assertNotNull( account );
		assertEquals( ACCOUNTS_1.get( 2L ), account);
		assertNotEquals( ACCOUNTS_1.get( 1L ), account);
		
		account = accountService.getUserAccountById( 2L, 3L );
		assertNotNull( account );
		assertEquals( ACCOUNTS_2.get( 3L ), account);
		assertNotEquals( ACCOUNTS_2.get( 4L ), account);
		
		account = accountService.getUserAccountById( 2L, 4L );
		assertNotNull( account );
		assertEquals( ACCOUNTS_2.get( 4L ), account);
		assertNotEquals( ACCOUNTS_2.get( 3L ), account);
	}	

}
