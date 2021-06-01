package com.ecommerce.backend.controllers.exceptions;

import java.time.Instant;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ecommerce.backend.exceptions.EntityNotFoundException;
import com.ecommerce.backend.models.exceptions.ResponseError;
import com.ecommerce.backend.utils.MessagesConstants;

@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ResponseError> entityNotFound(EntityNotFoundException e, HttpServletRequest request) {
		ResponseError error = new ResponseError();
		error.setTimestamp(Instant.now());
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setError(MessagesConstants.NO_DATA);
		error.setMessage(e.getLocalizedMessage());
		error.setPath(request.getRequestURI());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

}
