package com.addressbook.requet.validator;

import com.addressbook.enums.RequestType;

/**
 * Factory method to get request validator corresponding to the provided request
 * type
 * 
 * @author Vrushali
 *
 */
public class RequestValidatorFactory {
	public static AddressBookRequestValidator getRequestValidator(RequestType requestType) {
		if (RequestType.CREATE_ADDRESS_BOOK_RECORD_REQUEST.equals(requestType))
			return new CreateRequestValidator();
		else if (RequestType.UPDATE_ADDRESS_BOOK_RECORD_REQUEST.equals(requestType))
			return new UpdateRequestValidator();
		else if (RequestType.GET_ADDRESS_BOOK_RECORD_REQUEST.equals(requestType))
			return new GetRequestValidator();
		else if (RequestType.DELETE_ADDRESS_BOOK_RECORD_REQUEST.equals(requestType))
			return new DeleteRequestValidator();
		return null;
	}
}
