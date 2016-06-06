package com.addressbook.exception;

import com.addressbook.service.response.ErrorMessageEnum;

/**
 * Throws to indicate occurrence of error while executing address book application request
 * 
 * @author Vrushali
 *
 */
public class AddresBookException extends Exception {
	/** serial version id */
	private static final long serialVersionUID = 4491458182177180786L;

	/** Cause of the exception */
	private Throwable cause;

	/** Exception error message */
	private ErrorMessageEnum errorMessageEnum;

	public AddresBookException(ErrorMessageEnum errorMessageEnum, Throwable cause) {
		this.errorMessageEnum = errorMessageEnum;
		this.cause = cause;
	}

	/**
	 * @return the throwable
	 */
	public Throwable getCause() {
		return cause;
	}

	/**
	 * @param throwable
	 *            the throwable to set
	 */
	public void setCause(Throwable cause) {
		this.cause = cause;
	}

	/**
	 * @return the errorMessageEnum
	 */
	public ErrorMessageEnum getErrorMessageEnum() {
		return errorMessageEnum;
	}

	/**
	 * @param errorMessageEnum
	 *            the errorMessageEnum to set
	 */
	public void setErrorMessageEnum(ErrorMessageEnum errorMessageEnum) {
		this.errorMessageEnum = errorMessageEnum;
	}
}
