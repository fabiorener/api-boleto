package com.contaazul.boleto.api.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import com.contaazul.boleto.api.domain.Bankslip;

public interface BankslipService {
	
	Bankslip createBankslip(Bankslip bankslip);
	List<Bankslip> getAllBankslip();
	Bankslip getDetailsBankslip(String id)throws UnsupportedEncodingException;
	Bankslip paymentBankslip(String id, Date paymentDate) throws UnsupportedEncodingException;
	Bankslip cancelBankslip(String id) throws UnsupportedEncodingException;

}
