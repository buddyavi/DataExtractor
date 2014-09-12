package com.usc.avi.exceptions;

/**
 * Custom Exception for handling if student is not enrolled
 */
public class StudentNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public StudentNotFoundException(String msg) {
		super(msg);
	}

	/**
	 * 
	 * @param throwable
	 */
	public StudentNotFoundException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * 
	 * @param msg
	 * @param throwable
	 */
	public StudentNotFoundException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

}
