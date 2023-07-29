package com.ajisegiri.usermanagement.exceptions.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class ValidationHandler extends ResponseEntityExceptionHandler{
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		List<FieldError> errorList = ex.getBindingResult().getFieldErrors();
		String errorMessage = errorList.stream()
				.map(fieldError -> fieldError.getField() + " - " + fieldError.getDefaultMessage())
				.sorted()
				.collect(Collectors.joining(", "));
		log.info("errorMessage : {} ", errorMessage);
		return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);

	}


}
