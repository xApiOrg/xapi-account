package com.santander.xapi.account.service;

import java.util.Set;

public interface AccountService {
	public static final String IBAN_VALIDATOR = "https://openiban.com/validate/"; // https://openiban.com/validate/IBAN_NUMBER?getBIC=true&validateBankCode=true
	
	// https://github.com/mangrep/ifsc-rest-api
	public static final String IFSC_VALIDATOR = "https://api.techm.co.in/api/v1/ifsc/"; // https://api.techm.co.in/api/v1/ifsc/SBIN0000138
	public static final String MICR_VALIDATOR = "https://api.techm.co.in/api/v1/micr/"; // https://api.techm.co.in/api/v1/micr/842002002
	
	public static final String ABA_RTN_VALIDATOR = "http://www.routingnumbers.info/api/name.json?rn="; // http://www.routingnumbers.info/api/name.json?rn=324377516
	
	public Set<Object> getAllPayableAccounts(Long userId);
	public Object getUserAccountById(Long userId, Long id);
}
