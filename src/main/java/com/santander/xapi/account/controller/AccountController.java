package com.santander.xapi.account.controller;

import java.io.Serializable;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.santander.xapi.account.service.AccountService;


@RestController
@RequestMapping("/account")
public class AccountController {
	public static final Logger logger = LoggerFactory.getLogger(AccountController.class);
	
	@Autowired AccountService accountService;
/**
 * 
	/account/{user_id}						GET		- getAllUserPaymentAccounts 	All user payment accounts		http://localhost:10001/account/100
	/account/{user_id}/{account_id}			GET		- getUserAccountById			User's account details			http://localhost:10001/account/100/200

 */
	
	@CrossOrigin(origins="*")
	@RequestMapping(value = "/{user_id}", method = RequestMethod.GET)
	public ResponseEntity<?> getAllUserPaymentAccounts(@PathVariable("user_id") Long userId){ //ResponseEntity<Collection<PaymentAccounts>>
		String info = //"\nMetod getAllUserPaymentAccounts( Integer userId) NOT IMPLEMENTED YET" + 
				"\nGet ALL User's PAYMENT accounts by user Id" + "\n Parameters, user Id = " + userId;		
		logger.info(info);
		
		Set<Object> accounts = accountService.getAllPayableAccounts(userId);// .getAll( userId );
//		logger.info(accounts != null  && ! accounts.isEmpty() ? accounts.toString(): "NO AccountS FOUND!!!");

//		return new ResponseEntity<List<Account>>(accounts, accounts != null && ! accounts.isEmpty()?  HttpStatus.OK: HttpStatus.NOT_FOUND);
		return new ResponseEntity<Set<Object>>(accounts, HttpStatus.I_AM_A_TEAPOT);
	}	
	
	@CrossOrigin
	@RequestMapping(value = "/{user_id}/{account_id}", method = RequestMethod.GET)
	public ResponseEntity<?> getUserAccountById(
			@PathVariable("user_id") Long userId, @PathVariable("account_id") Long accountId){ //ResponseEntity<Collection<PaymentAccounts>>

		String info = //"\nMetod getUserAccountById( Integer userId, Integer accountId) NOT IMPLEMENTED YET" + 
			"\nGet User's PAYMENT account by user Id and account Id" + 
			"\n Parameters, user Id = " + userId + ", account Id = " + accountId;		
		logger.info(info);
		
		// getAccountById(accountId) - should return same result if accountId is unique across all system
		Object account = accountService.getUserAccountById(userId, accountId);
		logger.info(account != null? account.toString(): "Account NOT FOUND!!!");
		
		return new ResponseEntity<Object>(account, account != null? HttpStatus.OK: HttpStatus.NOT_FOUND);
	}

}

class PayeeDto implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String name;
	
	public PayeeDto(){}
	public PayeeDto(String name){ this.name = name;}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}

/*
{
    "timestamp": 1504795569656,
    "status": 400,
    "error": "Bad Request",
    "exception": "org.springframework.http.converter.HttpMessageNotReadableException",
    "message": "Required request body is missing: public org.springframework.http.ResponseEntity<?> com.xapi.account.controller.AccountController.createUserPayeeAccount(java.lang.String,java.lang.Integer)",
    "path": "/ipay/account/payee/100"
}

to Avoid add in postman or in real request 
Body -> raw -> "account any string" but NOT NOTHING 

{
    "timestamp": 1504796642464,
    "status": 415,
    "error": "Unsupported Media Type",
    "exception": "org.springframework.web.HttpMediaTypeNotSupportedException",
    "message": "Content type 'text/plain;charset=UTF-8' not supported",
    "path": "/ipay/account/payee/100"
}
		Content-Type = text/xml
		<properties>
			<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		</properties>
		
or 		Content-Type = application/json	
		{"some": "jason"}
 * */

/*
{
    "timestamp": 1504785871789,
    "status": 415,
    "error": "Unsupported Media Type",
    "exception": "org.springframework.web.HttpMediaTypeNotSupportedException",
    "message": "Content type 'text/plain;charset=UTF-8' not supported",
    "path": "/ipay/account/payee/100/10"
}

{
    "timestamp": 1504786033592,
    "status": 400,
    "error": "Bad Request",
    "exception": "org.springframework.http.converter.HttpMessageNotReadableException",
    "message": "Required request body is missing: public org.springframework.http.ResponseEntity<?> com.xapi.account.controller.AccountController.updateUserPayeeAccount(java.lang.Integer,java.lang.Integer,java.lang.Object)",
    "path": "/ipay/account/payee/100/10"
}

	Content-Type = text/xml or application/xml 	and 	Body -> raw -> <xml>some xml</xml>
	Content-Type = text/json 					and 	Body -> raw -> {"some": "jason"}

https://stackoverflow.com/questions/19468572/spring-mvc-why-not-able-to-use-requestbody-and-requestparam-together
 * */	
