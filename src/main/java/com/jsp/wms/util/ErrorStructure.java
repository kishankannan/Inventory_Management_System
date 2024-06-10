package com.jsp.wms.util;

import java.util.Map;

public class ErrorStructure<T> {

	private int status;
	private String message;
	private T rootcause;
	
	public int getStatus() {
		return status;
	}
	public String getMessage() {
		return message;
	}
	public T getRootcause() {
		return rootcause;
	}
	public ErrorStructure setStatus(int status) {
		this.status = status;
		return this;
	}
	public ErrorStructure setMessage(String message) {
		this.message = message;
		return this;
	}
	public ErrorStructure setRootcause(T rootcause) {
		this.rootcause = rootcause;
		return this;
	}
}
