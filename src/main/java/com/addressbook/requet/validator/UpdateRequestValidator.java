package com.addressbook.requet.validator;

import java.util.ArrayList;
import java.util.List;

import com.addressbook.response.model.AddressDetails;
import com.addressbook.response.model.ContactNumberDetails;
import com.addressbook.response.model.EmailAddressDetails;
import com.addressbook.response.model.PersonDetails;
import com.addressbook.service.request.AddressBookRequest;
import com.addressbook.service.response.ErrorMessageEnum;
import com.addressbook.service.response.ErrorMessages;

/**
 * {@link AddressBookRequestValidator} implementation to validate update request
 * 
 * @author Vrushali
 *
 */
public class UpdateRequestValidator implements AddressBookRequestValidator {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ErrorMessages> validateRequest(AddressBookRequest request) {
		List<ErrorMessages> errorMessages = new ArrayList<ErrorMessages>();

		if (request == null || request.getPersonDetails() == null || request.getPersonDetails().isEmpty()) {
			errorMessages.add(new ErrorMessages(ErrorMessageEnum.INVALID_REQUEST, null));
		} else {
			for (PersonDetails personDetails : request.getPersonDetails()) {
				// validate person id to update
				if (personDetails.getId() == null) {
					errorMessages.add(new ErrorMessages(ErrorMessageEnum.PERSON_ID_MISSING_FROM_UPDATE_REQUEST, null));
				} else {
					if (personDetails.getAddressDetails() != null && !personDetails.getAddressDetails().isEmpty()) {
						// validate address request
						for (AddressDetails addressDetails : personDetails.getAddressDetails()) {
							if (addressDetails != null && addressDetails.getId() == null) {
								errorMessages.add(new ErrorMessages(
										ErrorMessageEnum.ADDRESS_ID_MISSING_FROM_UPDATE_REQUEST, null));
							}
						}
					}

					if (personDetails.getEmailAddressDetails() != null
							&& !personDetails.getEmailAddressDetails().isEmpty()) {
						// validate email address request
						for (EmailAddressDetails emailAddressDetails : personDetails.getEmailAddressDetails()) {
							if (emailAddressDetails != null && emailAddressDetails.getId() == null) {
								errorMessages.add(new ErrorMessages(
										ErrorMessageEnum.EMAIL_ADDRESS_ID_MISSING_FROM_UPDATE_REQUEST, null));
							}

							if (emailAddressDetails.getEmailAddress() == null
									|| !emailAddressDetails.getEmailAddress().endsWith(".com")) {
								errorMessages
										.add(new ErrorMessages(ErrorMessageEnum.INVALID_EMAIL_ADDRESS_FORMAT, null));
							}
						}
					}

					if (personDetails.getContactNumberDetails() != null
							&& !personDetails.getContactNumberDetails().isEmpty()) {
						// validate contact number request
						for (ContactNumberDetails contactNumberDetails : personDetails.getContactNumberDetails()) {
							if (contactNumberDetails != null && contactNumberDetails.getId() == null) {
								errorMessages.add(new ErrorMessages(
										ErrorMessageEnum.CONTACT_NUMBER_ID_MISSING_FROM_UPDATE_REQUEST, null));
							}

							if (contactNumberDetails.getContactNumber() == null
									|| (contactNumberDetails.getContactNumber() != null
											&& Long.toString(contactNumberDetails.getContactNumber()).length() > 10)) {
								errorMessages.add(new ErrorMessages(ErrorMessageEnum.INVALID_CONTACT_NUMBER, null));
							}
						}
					}
				}
			}
		}

		return errorMessages;
	}

}
