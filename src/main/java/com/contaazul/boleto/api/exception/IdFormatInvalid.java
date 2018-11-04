package com.contaazul.boleto.api.exception;

public class IdFormatInvalid extends RuntimeException {

	private static final long serialVersionUID = -5893430729377801567L;

	public IdFormatInvalid(String message) {
        super(message);
    }
	
}
