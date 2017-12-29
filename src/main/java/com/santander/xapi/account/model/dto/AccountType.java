package com.santander.xapi.account.model.dto;

public enum AccountType {
	CURRENT( 1 ), FX( 2 ), DEBIT( 3 ), CASH( 4 ), 
	CREDIT( 5 ), LOAN( 6 ), MMA( 7 ), MORTGAGE( 8 ),  
	ISA( 9 ), SAVING( 10 ), DEPOSIT( 11 );
	
	private AccountType(){}	
	private AccountType(Integer index){}
}
