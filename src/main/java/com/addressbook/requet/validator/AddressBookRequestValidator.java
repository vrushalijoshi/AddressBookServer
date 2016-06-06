package com.addressbook.requet.validator;

import java.util.List;

import com.addressbook.service.request.AddressBookRequest;
import com.addressbook.service.response.ErrorMessages;

/**
 * Interface provides method to validate the address book request
 * 
 * @author Vrushali
 *
 */
public interface AddressBookRequestValidator {
	/**
	 * Performs basic validations on the provided request
	 * 
	 * @param request
	 *            {@link AddressBookRequest}
	 * @return {@link List<ErrorMessages>} list of error messages
	 */
	public List<ErrorMessages> validateRequest(AddressBookRequest request);
}
