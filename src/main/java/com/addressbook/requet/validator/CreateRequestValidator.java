package com.addressbook.requet.validator;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.addressbook.response.model.ContactNumberDetails;
import com.addressbook.response.model.EmailAddressDetails;
import com.addressbook.response.model.PersonDetails;
import com.addressbook.service.request.AddressBookRequest;
import com.addressbook.service.response.ErrorMessageEnum;
import com.addressbook.service.response.ErrorMessages;

/**
 * {@link AddressBookRequestValidator} implementation to validate create request
 * 
 * @author Vrushali
 *
 */
public class CreateRequestValidator implements AddressBookRequestValidator {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ErrorMessages> validateRequest(AddressBookRequest request) {
		List<ErrorMessages> errorMessages = new ArrayList<ErrorMessages>();

		if (request == null || (request != null && request.getPersonDetails() == null)
				|| (request != null && request.getPersonDetails() != null && request.getPersonDetails().isEmpty())) {
			errorMessages.add(new ErrorMessages(ErrorMessageEnum.INVALID_REQUEST, null));
		} else {
			for (PersonDetails personDetail : request.getPersonDetails()) {
				if ((personDetail != null && (personDetail.getFirstName() == null || StringUtils.isEmpty(personDetail.getFirstName())))
						|| (personDetail != null && (personDetail.getLastName() == null || StringUtils.isEmpty(personDetail.getLastName())))
						|| (personDetail != null && (personDetail.getMiddleName() == null || StringUtils.isEmpty(personDetail.getMiddleName())))) {
					errorMessages.add(new ErrorMessages(ErrorMessageEnum.INVALID_NAME_ERROR, null));
				}
				
				if(personDetail != null && personDetail.getDateOfBirth() == null){
					errorMessages.add(new ErrorMessages(ErrorMessageEnum.INVALID_DATE_OF_BIRTH,null));
				}
				
				

				if (personDetail.getEmailAddressDetails() == null || (personDetail.getEmailAddressDetails() != null
						&& personDetail.getEmailAddressDetails().isEmpty())) {
					errorMessages.add(new ErrorMessages(ErrorMessageEnum.MISSING_EMAIL_ADDRESS, null));
				} else {
					for (EmailAddressDetails emailAddressDetails : personDetail.getEmailAddressDetails()) {
						if (emailAddressDetails.getEmailAddress() == null
								|| (emailAddressDetails.getEmailAddress() != null
										&& !emailAddressDetails.getEmailAddress().endsWith(".com"))) {
							errorMessages.add(new ErrorMessages(ErrorMessageEnum.INVALID_EMAIL_ADDRESS_FORMAT,
									emailAddressDetails.getEmailAddress()));
						}
					}
				}

				if (personDetail.getContactNumberDetails() == null || (personDetail.getContactNumberDetails() != null
						&& personDetail.getContactNumberDetails().isEmpty())) {
					errorMessages.add(new ErrorMessages(ErrorMessageEnum.MISSING_CONTACT_NUMBER, null));
				} else {
					for (ContactNumberDetails contactNumberDetails : personDetail.getContactNumberDetails()) {
						if (contactNumberDetails.getContactNumber() == null
								|| (contactNumberDetails.getContactNumber() != null
										&& Long.toString(contactNumberDetails.getContactNumber()).length() > 10)) {
							errorMessages.add(new ErrorMessages(ErrorMessageEnum.INVALID_CONTACT_NUMBER,
									Long.toString(contactNumberDetails.getContactNumber())));
						}
					}
				}

				if (personDetail.getAddressDetails() == null
						|| (personDetail.getAddressDetails() != null && personDetail.getAddressDetails().isEmpty())) {
					errorMessages.add(new ErrorMessages(ErrorMessageEnum.MISSING_ADDRESS, null));
				}
			}
		}
		return errorMessages;
	}

}
