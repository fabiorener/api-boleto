package com.contaazul.boleto.api.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contaazul.boleto.api.domain.Bankslip;
import com.contaazul.boleto.api.enums.StatusEnum;
import com.contaazul.boleto.api.exception.RecordNotFound;
import com.contaazul.boleto.api.exception.RecordNotUpdated;
import com.contaazul.boleto.api.repository.BankslipRepository;
import com.contaazul.boleto.api.service.BankslipService;


@Service
public class BankslipServiceImpl implements BankslipService  {

	@Autowired
	private BankslipRepository repository;
	
	public List<Bankslip> getAllBankslip() {

		List<Bankslip> list = new ArrayList<>();
		repository.findAll().forEach(e -> list.add(e));
		return list;

	}

	public Bankslip createBankslip(Bankslip bankslip) {

		bankslip.setStatus(StatusEnum.PENDING);

		return this.repository.save(bankslip);

	}	

	public Bankslip cancelBankslip(String id) throws UnsupportedEncodingException {

		UUID uuid = UUID.fromString(id);

		Optional <Bankslip> bankslip = repository.findById(uuid);


		if(!bankslip.isPresent()) {
			throw new RecordNotFound("Record not found!");
		}
	
		bankslip.get().setStatus(StatusEnum.CANCELED);
		
		int result = this.repository.updateCancel(bankslip.get().getId(), bankslip.get().getStatus());

		Optional <Bankslip> canceled = null;
		
		if(result > 0) {
			
			canceled = repository.findById(uuid);
			
		}else {
			
			throw new RecordNotUpdated("Record Not Updated!");
			
		}
		
		
		return canceled.get();
	}
	
	public Bankslip getDetailsBankslip(String id) throws UnsupportedEncodingException {
		UUID uuid = UUID.fromString(id);

		Optional <Bankslip> bankslip = repository.findById(uuid);

		if(!bankslip.isPresent()) {
			throw new RecordNotFound("Record not found!");
		}
		
		bankslip.get().setFine(calculateFine(bankslip.get().getTotalInCents(),bankslip.get().getDueDate()));

		return bankslip.get();
	}

	public Bankslip paymentBankslip(String id, Date paymentDate) throws UnsupportedEncodingException {
		UUID uuid = UUID.fromString(id);

		Optional <Bankslip> bankslip = repository.findById(uuid);


		if(!bankslip.isPresent()) {
			throw new RecordNotFound("Record not found!");
		}

		bankslip.get().setStatus(StatusEnum.PAID);
		bankslip.get().setPaymentDate(paymentDate);
		
		int result = this.repository.updatePayment(bankslip.get().getId(), bankslip.get().getStatus(),bankslip.get().getPaymentDate());

		Optional <Bankslip> paid = null;
		
		if(result > 0) {
			
			paid = repository.findById(uuid);
			
		}else {
			
			throw new RecordNotUpdated("Record Not Updated!");
			
		}

		
		return paid.get();
	}	
	
	
	private BigDecimal calculateFine(BigDecimal v, Date dueDate) {
		
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Instant instant = dueDate.toInstant();
		
		LocalDate due = instant.atZone(defaultZoneId).toLocalDate();
		LocalDate current = LocalDate.now();
		
		long diferencaEmDias = ChronoUnit.DAYS.between(due,current);
		
		BigDecimal fine = null;
		
		if(diferencaEmDias >  0 &&  diferencaEmDias <=10 ) {
			//0.5%
			fine = v.multiply(new BigDecimal(0.005));
			fine = fine.setScale(0,BigDecimal.ROUND_HALF_UP);
			
		}else if(diferencaEmDias > 10 ) {
			//1.0%
			fine = v.multiply(new BigDecimal(0.01));
			fine = fine.setScale(0,BigDecimal.ROUND_HALF_UP);
		}else {
			fine = new BigDecimal(0);
		}

		return fine;
		
	}

}
