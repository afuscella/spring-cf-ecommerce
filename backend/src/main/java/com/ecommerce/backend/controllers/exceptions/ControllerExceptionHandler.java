package com.ecommerce.backend.controllers.exceptions;

import java.time.Instant;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ecommerce.backend.exceptions.DatabaseIntegrityException;
import com.ecommerce.backend.exceptions.ResourceNotFoundException;
import com.ecommerce.backend.models.exceptions.ResponseError;
import com.ecommerce.backend.utils.MessagesConstants;

@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ResponseError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		ResponseError error = new ResponseError();

		error.setTimestamp(Instant.now());
		error.setStatus(status.value());
		error.setError(MessagesConstants.NO_FOUND);
		error.setMessage(e.getLocalizedMessage());
		error.setPath(request.getRequestURI());
		return ResponseEntity.status(status.value()).body(error);
	}

	@ExceptionHandler(DatabaseIntegrityException.class)
	public ResponseEntity<ResponseError> databaseIntegrityViolation(DatabaseIntegrityException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ResponseError error = new ResponseError();

		error.setTimestamp(Instant.now());
		error.setStatus(status.value());
		error.setError(MessagesConstants.BAD_REQUEST);
		error.setMessage(e.getLocalizedMessage());
		error.setPath(request.getRequestURI());
		return ResponseEntity.status(status.value()).body(error);
	}

}
