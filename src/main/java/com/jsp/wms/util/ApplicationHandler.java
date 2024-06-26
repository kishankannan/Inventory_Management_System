package com.jsp.wms.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jsp.wms.exception.AddressNotFoundByIdException;
import com.jsp.wms.exception.AdminNotFoundByEmailException;
import com.jsp.wms.exception.AdminNotFoundByIdException;
import com.jsp.wms.exception.ClientNotFoundByIdException;
import com.jsp.wms.exception.IllegalOperationException;
import com.jsp.wms.exception.InventoryNotFoundByIdException;
import com.jsp.wms.exception.StorageNotFoundByIdException;
import com.jsp.wms.exception.StorageTypeNotFoundByIdException;
import com.jsp.wms.exception.WareHouseNotFoundByCityException;
import com.jsp.wms.exception.WarehouseNotFoundByIdException;

@RestControllerAdvice
public class ApplicationHandler {

	public ResponseEntity<ErrorStructure<String>> errorResponse(HttpStatus status , String message , String rootCause){
		return ResponseEntity.status(status)
				.body(new ErrorStructure<String>()
						.setStatus(status.value())
						.setMessage(message)
						.setRootcause(rootCause));
	}

	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleIllegalOperation(IllegalOperationException ex){
		return errorResponse(HttpStatus.FOUND, ex.getMessage(), "Super_Admin Already Exists");
	}


	@ExceptionHandler
	public ResponseEntity<ErrorStructure<Map<String,String>>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex){
		List<ObjectError> errors =  ex.getAllErrors();

		Map<String, String> allErrors = new HashMap<String, String>();
		errors.forEach(error ->{
			FieldError fieldError = (FieldError) error;
			String field = fieldError.getField();
			String message = fieldError.getDefaultMessage();
			allErrors.put(field, message);
		});
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(new ErrorStructure<Map<String,String>>()
						.setStatus(HttpStatus.BAD_REQUEST.value())
						.setMessage("invalid input")
						.setRootcause(allErrors));
	}

	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleWarehouseNotFoundById(WarehouseNotFoundByIdException ex){
		return errorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), "Warehouse id not found");
	}

	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleUsernameNotFound(UsernameNotFoundException ex){
		return errorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), "Username not found in the database");
	}//is a predefined exception

	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleAdminNotFoundByEmail(AdminNotFoundByEmailException ex){
		return errorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), "Admin email is not found in the database");
	}

	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleAdminNotFoundById(AdminNotFoundByIdException ex){
		return errorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), "Admin id is not found in the database");
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleWarehouseNotFoundByCityException(WareHouseNotFoundByCityException ex){
		return errorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), "Warehouse not found by city");
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleAddressNotFoundByIdException(AddressNotFoundByIdException ex){
		return errorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), "Address not found by id");
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleClientNotFoundByIdException(ClientNotFoundByIdException ex){
		return errorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), "Client not found by id");
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleStorageNotFoundByIdException(StorageNotFoundByIdException ex){
		return errorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), "Storage not found by id");
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleStorageTypeNotFoundByIdException(StorageTypeNotFoundByIdException ex){
		return errorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), "Storage Type not found by id");
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleInventoryNotFoundByIdException(InventoryNotFoundByIdException ex){
		return errorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), "Inventory not found by id");
	}
}
