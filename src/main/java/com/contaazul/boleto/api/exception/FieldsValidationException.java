package com.contaazul.boleto.api.exception;

import org.springframework.validation.BindingResult;

public class FieldsValidationException extends RuntimeException  {
	
	
	private static final long serialVersionUID = 880380607103175672L;
	
	 private BindingResult result;

	public FieldsValidationException(String message, BindingResult result) {
        super(message);
        this.setResult(result);
    }

	public BindingResult getResult() {
		return result;
	}

	public void setResult(BindingResult result) {
		this.result = result;
	}

	
	
}
