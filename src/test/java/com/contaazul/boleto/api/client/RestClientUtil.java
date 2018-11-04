package com.contaazul.boleto.api.client;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.contaazul.boleto.api.domain.Bankslip;

public class RestClientUtil {


	public void createBankslip() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8080/rest/bankslips";
		Bankslip obj = new Bankslip();
		obj.setCustomer("customer");
		obj.setDueDate(new Date());
		obj.setTotalInCents(new BigDecimal(0));
		HttpEntity<Bankslip> requestEntity = new HttpEntity<Bankslip>(obj, headers);

		try {
		
			ResponseEntity<Bankslip> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Bankslip.class);
			Bankslip bankslip = responseEntity.getBody();
			System.out.println("=================createBankslip==========================");      
			System.out.println(bankslip);      
			
		}catch (HttpClientErrorException e) {
			System.out.println("================= ERRO: createBankslip==========================");      
			System.out.println(e.getResponseBodyAsString());      
		}

	}
	public void getDetailsBankslip() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8080/rest/bankslips/{id}";
		HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
		
		try {
			ResponseEntity<Bankslip> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Bankslip.class, "34bcec08-1b2d-4fd5-9ef3-664dc3d063c0");
			Bankslip bankslip = responseEntity.getBody();
			System.out.println("=================getDetailsBankslip==========================");      
			System.out.println(bankslip);      
			
		}catch (HttpClientErrorException e) {
			System.out.println("================= ERRO: getAllBankslip==========================");      
			System.out.println(e.getResponseBodyAsString());      
		}
	}


	public void getAllBankslip() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8080/rest/bankslips";
		HttpEntity<String> requestEntity = new HttpEntity<String>(headers);

		try {
			ResponseEntity<Bankslip[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Bankslip[].class);
			Bankslip[] bankslips = responseEntity.getBody();
			System.out.println("=================getAllBankslip==========================");      

			for(Bankslip bankslip : bankslips) {
				System.out.println(bankslip);      
			}
			
		}catch (HttpClientErrorException e) {
			System.out.println("================= ERRO: getAllBankslip==========================");      
			System.out.println(e.getResponseBodyAsString());      
		}
		
	}	

	public void paymentBankslip() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8080/rest/bankslips/{id}/payments";
		Bankslip obj = new Bankslip();
		obj.setPaymentDate(new Date());
		HttpEntity<Bankslip> requestEntity = new HttpEntity<Bankslip>(obj, headers);

		try {
			ResponseEntity<Void> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Void.class,"34bcec08-1b2d-4fd5-9ef3-664dc3d063c0");
			int status = responseEntity.getStatusCodeValue();
			System.out.println("=================paymentBankslip==========================");      
			System.out.println("Status -> " + status);      
			
		}catch (HttpClientErrorException e) {
			System.out.println("================= ERRO: paymentBankslip==========================");      
			System.out.println(e.getResponseBodyAsString());      
		}

	}

	public void cancelBankslip() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8080/rest/bankslips/{id}";
		HttpEntity<Bankslip> requestEntity = new HttpEntity<Bankslip>(headers);

		try {
			ResponseEntity<Void> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, Void.class, "f978a31c-6fa4-4217-8dce-f57dfb5897d3");        

			int status = responseEntity.getStatusCodeValue();
			System.out.println("=================cancelBankslip==========================");      
			System.out.println("Status -> " + status);      
			
		}catch (HttpClientErrorException e) {
			System.out.println("================= ERRO: cancelBankslip==========================");      
			System.out.println(e.getResponseBodyAsString());      
		}
	}

	public static void main(String args[]) {
		RestClientUtil util = new RestClientUtil();
		util.createBankslip();
		util.getDetailsBankslip();
		util.getAllBankslip();
		util.paymentBankslip();
		util.cancelBankslip();

	}    
}
