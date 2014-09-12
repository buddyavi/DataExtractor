package com.usc.avi.exceptions;

/**
 * Custom Exception for handling if the request is invalid
 */
public class InvalidRequestException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidRequestException(String msg) {
		super(msg);
	}

	/**
	 * 
	 * @param throwable
	 */
	public InvalidRequestException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * 
	 * @param msg
	 * @param throwable
	 */
	public InvalidRequestException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

}
