package com.santander.xapi.account.model.dto;

import java.io.Serializable;


public class Account  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
//	private final User user;
//	private final Date created = new Date();
	private String currency;
	private Double balance = 0.0;
	private Double overDraft = 0.0;
	private AccountType type;
//	private Set<Payment> payments;
//	private AccountDetails accountDetails;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public Double getOverDraft() {
		return overDraft;
	}
	public void setOverDraft(Double overDraft) {
		this.overDraft = overDraft;
	}
	public AccountType getType() {
		return type;
	}
	public void setType(AccountType type) {
		this.type = type;
	}
														
//	public Account(){ this.user = new User();}
//	public Account(User user){ this.user = user;}
	
}

