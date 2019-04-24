package com.ideacrest.app.exception;

import javax.ws.rs.core.Response.Status;

public class HystrixServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private int statusCode;

	private String statusMessage;

	private String errorMessage;

	public HystrixServiceException(String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
		this.statusCode = Status.INTERNAL_SERVER_ERROR.getStatusCode();
		this.statusMessage = Status.INTERNAL_SERVER_ERROR.getReasonPhrase();
	}

	public HystrixServiceException(String errorMessage, Throwable cause) {
		super(errorMessage, cause);
		this.errorMessage = errorMessage;
		this.statusCode = Status.INTERNAL_SERVER_ERROR.getStatusCode();
		this.statusMessage = Status.INTERNAL_SERVER_ERROR.getReasonPhrase();
	}

	public HystrixServiceException(int statusCode, String statusMessage, String errorMessage, Throwable cause) {
		super(errorMessage, cause);
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
		this.errorMessage = errorMessage;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
