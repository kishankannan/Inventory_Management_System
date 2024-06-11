package com.jsp.wms.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@SuppressWarnings("serial")
@Getter
@AllArgsConstructor
public class WarehouseNotFoundByIdException extends RuntimeException {

	private String message;
}
