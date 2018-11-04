package com.contaazul.boleto.api.resource.rest;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.contaazul.boleto.api.domain.Bankslip;
import com.contaazul.boleto.api.exception.FieldsValidationException;
import com.contaazul.boleto.api.exception.IdFormatInvalid;
import com.contaazul.boleto.api.service.BankslipService;
import com.contaazul.boleto.api.to.ErrorDetails;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@Api(basePath = "/rest/bankslips",value = "Bankslip controller",description = "Main methods for bankslip management")
@RestController
@RequestMapping("/rest/bankslips")
@CrossOrigin(origins = "*")
public class BankslipRestController {

	@Autowired
	private BankslipService bankslipService;
	

	@ApiOperation(value = "Create Bank slip",notes = "Bankslip",consumes = "application/json",produces = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Bank slip created", response = Bankslip.class),
			@ApiResponse(code = 400, message = "Bank slip not provided in the request body", response = ErrorDetails.class),
			@ApiResponse(code = 422, message = "Invalid bank slip", response = ErrorDetails.class),
			@ApiResponse(code = 500, message = "Internal server error", response = ErrorDetails.class)}
			)
	@PostMapping()
	public ResponseEntity<Bankslip> createBankslip(@Valid @RequestBody  @ApiParam(value = "Bankslip that will be created", required = true, type = "body") Bankslip bankslip, BindingResult result) {

		if(result.hasErrors()) {
			throw new FieldsValidationException("Invalid data!",result);
		}

		Bankslip bankslipPersisted = bankslipService.createBankslip(bankslip);
		
		/*
		URI location = ServletUriComponentsBuilder
	                .fromCurrentRequest()
	                .path("/{id}")
	                .buildAndExpand(bankslipPersisted.getId())
	                .toUri();
		 */		

		return new ResponseEntity<Bankslip>(bankslipPersisted, HttpStatus.CREATED);
	}	
	
	
	@ApiOperation(value = "List Bank slip", produces = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Ok"),
			@ApiResponse(code = 500, message = "Internal server error", response = ErrorDetails.class)}
	)
	@GetMapping
	public ResponseEntity<List<Bankslip>>  getAllBankslip() {
		
		List<Bankslip> list = bankslipService.getAllBankslip();
		
		return new ResponseEntity<List<Bankslip>>(list, HttpStatus.OK);
	}
	
	
	@ApiOperation(value = " Details Bank slip", produces = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Ok"),
			@ApiResponse(code = 404, message = "Bankslip not found with the specified id", response = ErrorDetails.class),
			@ApiResponse(code = 500, message = "Internal server error", response = ErrorDetails.class)}
	)
	@GetMapping("/{id}")
	public ResponseEntity<Bankslip> getDetailsBankslip(@PathVariable("id") String id) {
		Bankslip bankslip = null;
		
		try {
			
			bankslip = bankslipService.getDetailsBankslip(id);
			
		} catch (UnsupportedEncodingException e) {
			throw new IdFormatInvalid("ID Format Invalid!");
		}
		
		return new ResponseEntity<Bankslip>(bankslip, HttpStatus.OK);
	}
	
	
	@ApiOperation(value = "Payment Bank slip",notes = "Bankslip",consumes = "application/json",produces = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "No content", response = Bankslip.class),
			@ApiResponse(code = 404, message = "Bankslip not found with the specified id", response = ErrorDetails.class),
			@ApiResponse(code = 500, message = "Internal server error", response = ErrorDetails.class)}
			)
	@PostMapping("/{id}/payments")
	public ResponseEntity<Void> paymentBankslip(@PathVariable("id") String id,@RequestBody Bankslip bankslip) {
		
		try {
			@SuppressWarnings("unused")
			Bankslip payment = bankslipService.paymentBankslip(id, bankslip.getPaymentDate());
		} catch (UnsupportedEncodingException e) {
			throw new IdFormatInvalid("ID Format Invalid!");
		}

		return ResponseEntity.noContent().build();
	}	
	
	
	@ApiOperation(value = "Cancel Bankslip", produces = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "204 : Bankslip canceled"),
			@ApiResponse(code = 404, message = "Bankslip not found with the specified id", response = ErrorDetails.class),
			@ApiResponse(code = 500, message = "Internal server error", response = ErrorDetails.class)}
	)
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> cancelBankslip(@PathVariable("id") String id) {
		
		try {
			
			@SuppressWarnings("unused")
			Bankslip  cancel = bankslipService.cancelBankslip(id);
			
		} catch (UnsupportedEncodingException e) {
			throw new IdFormatInvalid("ID Format Invalid!");
		}
		
		return ResponseEntity.noContent().build();
	}	

}
