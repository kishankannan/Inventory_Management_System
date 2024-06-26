package com.jsp.wms.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@SuppressWarnings("serial")
@Getter
@AllArgsConstructor
public class SpaceOrWeightNotAvailableException extends RuntimeException {

	private String message;
}
