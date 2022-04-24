package com.domingueti.dscatalog.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.domingueti.dscatalog.services.exceptions.DatabaseException;
import com.domingueti.dscatalog.services.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest http) {
		int notFound = HttpStatus.NOT_FOUND.value();
		StandardError err = new StandardError();
		err.setTimestamp(Instant.now());
		err.setStatus(notFound);
		err.setError("Resource not found");
		err.setMessage(e.getMessage());
		err.setPath(http.getRequestURI());
		return ResponseEntity.status(notFound).body(err);
	}
	
	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<StandardError> databaseException(DatabaseException e, HttpServletRequest http) {
		int badRequest = HttpStatus.BAD_REQUEST.value();
		StandardError err = new StandardError();
		err.setTimestamp(Instant.now());
		err.setStatus(badRequest);
		err.setError("Database exception");
		err.setMessage(e.getMessage());
		err.setPath(http.getRequestURI());
		return ResponseEntity.status(badRequest).body(err);
	}
	
}
