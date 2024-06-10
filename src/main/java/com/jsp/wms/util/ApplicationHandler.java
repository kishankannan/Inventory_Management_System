package com.jsp.wms.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jsp.wms.exception.AdminNotFoundByIdException;
import com.jsp.wms.exception.IllegalOperationException;

@RestControllerAdvice
public class ApplicationHandler {
	

	public <T> ResponseEntity<ErrorStructure<String>> errorResponse(HttpStatus status , String message , T rootCause){
		return ResponseEntity.status(status)
				.body(new ErrorStructure<>()
						.setStatus(status.value())
						.setMessage(message)
						.setRootcause(rootCause));
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleIllegalOperationException(IllegalOperationException ex){
		return errorResponse(HttpStatus.FOUND, ex.getMessage(), "Super_Admin Already Exists");
	}
	

	@ExceptionHandler
	public ResponseEntity<ErrorStructure<List<String>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
		List<ObjectError> errors = ex.getAllErrors();
		
		
		Map<String, String> allErrors = new HashMap<>();
		
		errors.forEach(error ->{
			FieldError fieldError = (FieldError) error;
			String field = fieldError.getField();
			String message = fieldError.getDefaultMessage();
			allErrors.put(field, message);
		});
		
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(new ErrorStructure<List<String>>()
						.setStatus(HttpStatus.BAD_REQUEST.value())
						.setMessage("Invalid Message")
						.setRootcause(allErrors));
						
	}
	
 
}
