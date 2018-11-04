package com.contaazul.boleto.api.service.test;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.contaazul.boleto.api.domain.Bankslip;
import com.contaazul.boleto.api.enums.StatusEnum;
import com.contaazul.boleto.api.exception.RecordNotFound;
import com.contaazul.boleto.api.service.BankslipService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BankslipServiceTest {
	
	@Autowired
	BankslipService service;
	
	SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");

	@Test
	public void createBankslipTest() throws ParseException {
		
		Bankslip bankslip = new Bankslip();
		bankslip.setDueDate(formatDate.parse("2018-01-01"));
		bankslip.setTotalInCents(new BigDecimal(100000));
		bankslip.setCustomer("Trillian Company");
		
		Bankslip bankslipPersited =  service.createBankslip(bankslip);

		System.out.println("createBankslipTest -> bankslipPersited.getId() = " + bankslipPersited.getId());
		
		Assert.assertTrue(bankslipPersited.getId()!=null && bankslipPersited.getStatus().equals(StatusEnum.PENDING));
		
	}
	
	@Test
	public void getAllBankslipTest() throws ParseException {
		
		Bankslip bankslip = new Bankslip();
		bankslip.setDueDate(formatDate.parse("2018-01-01"));
		bankslip.setTotalInCents(new BigDecimal(100000));
		bankslip.setCustomer("Trillian Company");
		bankslip.setStatus(StatusEnum.PENDING);
		
		@SuppressWarnings("unused")
		Bankslip bankslipPersited =  service.createBankslip(bankslip);		

		List<Bankslip> list = service.getAllBankslip();
		
		System.out.println("getAllBankslipTest -> list.size() =  " + list.size());
		
		Assert.assertTrue(list.size() > 0);
		
	}		
	
	@Test
	public void getDetailsBankslipTest() throws ParseException,UnsupportedEncodingException {
		
		Bankslip bankslip = new Bankslip();
		bankslip.setDueDate(formatDate.parse("2018-01-01"));
		bankslip.setTotalInCents(new BigDecimal(100000));
		bankslip.setCustomer("Trillian Company");
		
		Bankslip bankslipPersited =  service.createBankslip(bankslip);		

		Bankslip details= service.getDetailsBankslip(bankslipPersited.getId().toString());
		
		System.out.println("getDetailsBankslipTest ->  " + details);
		
		Assert.assertNotNull(details.getId());
		
	}	
	
	
	@Test(expected = RecordNotFound.class)
	public void recordNotFoundTest() throws UnsupportedEncodingException {
	
		@SuppressWarnings("unused")
		Bankslip bankslip  = service.getDetailsBankslip("cfda9905-3fa6-4460-b159-cabefc601525");
		
	}		
	
	@Test
	public void cancelBankslipTest() throws ParseException, UnsupportedEncodingException {
		
		Bankslip bankslip = new Bankslip();
		bankslip.setDueDate(formatDate.parse("2018-01-01"));
		bankslip.setTotalInCents(new BigDecimal(100000));
		bankslip.setCustomer("Trillian Company");
		
		Bankslip bankslipPersited =  service.createBankslip(bankslip);

		Bankslip bankslipCanceled = service.cancelBankslip(bankslipPersited.getId().toString());

		System.out.println("cancelBankslipTest -> bankslipCanceled.getStatus() = " + bankslipCanceled.getStatus());
		
		Assert.assertEquals(bankslipCanceled.getStatus(), StatusEnum.CANCELED);
		
	}	

	
	
	@Test
	public void paymentBankslipTest() throws ParseException, UnsupportedEncodingException {
		
		Bankslip bankslip = new Bankslip();
		bankslip.setDueDate(formatDate.parse("2018-01-01"));
		bankslip.setTotalInCents(new BigDecimal(100000));
		bankslip.setCustomer("Trillian Company");
		
		Bankslip bankslipPersited =  service.createBankslip(bankslip);
		
		Bankslip bankslipUpdated = service.paymentBankslip(bankslipPersited.getId().toString(),formatDate.parse("2018-01-10"));

		System.out.println("paymentBankslipTest -> bankslipUpdated.getStatus() = " + bankslipUpdated.getStatus() + " -> bankslipUpdated.getPaymentDate() = " + bankslipUpdated.getPaymentDate());
		
		Assert.assertEquals(bankslipUpdated.getStatus(), StatusEnum.PAID);
		
	}	

	
	
}
