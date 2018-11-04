package com.contaazul.boleto.api.exception;

public class RecordNotFound extends RuntimeException {

	private static final long serialVersionUID = -5893430729377801662L;

	public RecordNotFound(String message) {
        super(message);
    }
	
}
