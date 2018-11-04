package com.contaazul.boleto.api.resource.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import com.contaazul.boleto.api.exception.FieldsValidationException;
import com.contaazul.boleto.api.exception.IdFormatInvalid;
import com.contaazul.boleto.api.exception.RecordNotFound;
import com.contaazul.boleto.api.exception.RecordNotUpdated;
import com.contaazul.boleto.api.to.ErrorDetails;



@ControllerAdvice(annotations = RestController.class)
public class RestExceptionHandler  {


	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorDetails handleNotImplementedException(HttpMessageNotReadableException ex, WebRequest request) {


		return ErrorDetails.builder()
				.addDetail("Object not provided in the request body: " + ex.getMessage())
				.addDescription(ex.getMessage())
				.addStatus(HttpStatus.BAD_REQUEST)
				.addHttpMethod(getHttpMethod(request))
				.addPath(getPath(request))
				.build();
	}

	@ExceptionHandler({org.hibernate.exception.ConstraintViolationException.class})
	@ResponseStatus(value = HttpStatus.CONFLICT)
	@ResponseBody
	public ErrorDetails handleNotImplementedException(org.hibernate.exception.ConstraintViolationException ex,WebRequest request) {

		return ErrorDetails.builder()
				.addDetail("Constraint Violation: " + ex.getConstraintName())
				.addDescription(ex.getMessage())
				.addStatus(HttpStatus.CONFLICT)
				.addHttpMethod(getHttpMethod(request))
				.addPath(getPath(request))
				.build();
	}

	@ExceptionHandler({FieldsValidationException.class})
	@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
	@ResponseBody
	public ErrorDetails handleNotImplementedException(com.contaazul.boleto.api.exception.FieldsValidationException ex, WebRequest request) {

		List<String> listErros = new ArrayList<String>();
		ex.getResult().getAllErrors().forEach(error -> listErros.add(error.getDefaultMessage()));

		return ErrorDetails.builder()
				.addDetail("Object invalid  provided.The possible reasons are: ")
				.addDescription(ex.getMessage())
				.addErros(listErros)
				.addStatus(HttpStatus.UNPROCESSABLE_ENTITY)
				.addHttpMethod(getHttpMethod(request))
				.addPath(getPath(request))
				.build();
	}

	@ExceptionHandler({IdFormatInvalid.class})
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorDetails handleNotImplementedException(com.contaazul.boleto.api.exception.IdFormatInvalid ex, WebRequest request) {


		return ErrorDetails.builder()
				.addDetail("Validation: ")
				.addDescription(ex.getMessage())
				.addStatus(HttpStatus.BAD_REQUEST)
				.addHttpMethod(getHttpMethod(request))
				.addPath(getPath(request))
				.build();
	}

	@ExceptionHandler({RecordNotFound.class})
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ResponseBody
	public ErrorDetails handleNotImplementedException(com.contaazul.boleto.api.exception.RecordNotFound ex, WebRequest request) {


		return ErrorDetails.builder()
				.addDetail("Object not found with the specified id. ")
				.addDescription(ex.getMessage())
				.addStatus(HttpStatus.NOT_FOUND)
				.addHttpMethod(getHttpMethod(request))
				.addPath(getPath(request))
				.build();
	}

	@ExceptionHandler({RecordNotUpdated.class})
	@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
	@ResponseBody
	public ErrorDetails handleNotImplementedException(com.contaazul.boleto.api.exception.RecordNotUpdated ex, WebRequest request) {


		return ErrorDetails.builder()
				.addDetail("Object not updated.")
				.addDescription(ex.getMessage())
				.addStatus(HttpStatus.UNPROCESSABLE_ENTITY)
				.addHttpMethod(getHttpMethod(request))
				.addPath(getPath(request))
				.build();
	}
	
	@ExceptionHandler({Exception.class})
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ErrorDetails handleNotImplementedException(Exception ex, WebRequest request) {

		return ErrorDetails.builder()
				.addDetail("An exception was thrown.")
				.addDescription(ex.getMessage())
				.addStatus(HttpStatus.INTERNAL_SERVER_ERROR)
				.addHttpMethod(getHttpMethod(request))
				.addPath(getPath(request))
				.build();
	}	
	
	private String getPath(WebRequest request) {

		return ((ServletWebRequest) request).getRequest().getRequestURI();
	}

	private String getHttpMethod(WebRequest request) {

		return ((ServletWebRequest) request).getRequest().getMethod();
	}

}
