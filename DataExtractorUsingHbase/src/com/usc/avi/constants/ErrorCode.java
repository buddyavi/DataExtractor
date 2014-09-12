package com.usc.avi.constants;

/**
 * This documents the error codes and error messages used in the project
 * 
 * 
 */

public enum ErrorCode {
	SUCCESS(0000, "Success"), STUDENT_MISSING(0001,
			"Student Not Enrolled or missing or invalid"), APP_ERROR(0002,
			"Application error while processing the request "), INVALID_REQUEST(
			0003, "Invalid Request"), HBASE_CONNECTION_ERROR(0004,
			"Error Establishing Database Connection");

	private final int iRetCode;
	private final String sRetMessage;

	ErrorCode(int id, String message) {
		this.iRetCode = id;
		this.sRetMessage = message;
	}

	public int getRetCode() {
		return iRetCode;
	}

	public String getRetMessage() {
		return sRetMessage;
	}
}
