package com.addressbook.requet.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.addressbook.service.request.AddressBookRequest;
import com.addressbook.service.response.ErrorMessageEnum;
import com.addressbook.service.response.ErrorMessages;

/**
 * {@link AddressBookRequestValidator} implementation to validate delete request
 * 
 * @author Vrushali
 *
 */
public class DeleteRequestValidator implements AddressBookRequestValidator {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ErrorMessages> validateRequest(AddressBookRequest request) {
		List<ErrorMessages> errorMessages = new ArrayList<ErrorMessages>();
		if (request == null || (request != null && request.getIds().isEmpty())) {
			errorMessages.add(new ErrorMessages(ErrorMessageEnum.INCOMPLETE_DELETE_REQUEST, ""));
		} else {
			for (UUID uuid : request.getIds()) {
				if (uuid == null) {
					errorMessages.add(new ErrorMessages(ErrorMessageEnum.INVALID_ID, null));
				}
			}
		}
		return errorMessages;
	}

}
