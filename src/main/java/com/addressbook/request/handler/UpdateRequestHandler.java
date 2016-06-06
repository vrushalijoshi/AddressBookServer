package com.addressbook.request.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.addressbook.adaptor.AddressBookAdapter;
import com.addressbook.dataservice.AddressBookDataService;
import com.addressbook.exception.AddresBookException;
import com.addressbook.persist.model.AddressEntity;
import com.addressbook.persist.model.ContactNumberEntity;
import com.addressbook.persist.model.EmailAddressEntity;
import com.addressbook.persist.model.PersonEntity;
import com.addressbook.response.model.AddressDetails;
import com.addressbook.response.model.ContactNumberDetails;
import com.addressbook.response.model.EmailAddressDetails;
import com.addressbook.response.model.PersonDetails;
import com.addressbook.service.request.AddressBookRequest;
import com.addressbook.service.response.AddressBookResponse;
import com.addressbook.service.response.ErrorMessageEnum;
import com.addressbook.service.response.ErrorMessages;

/**
 * {@link AddressBookRequestHandler} implementation to update request. Updates
 * the record based on provided details.
 * 
 * @author Vrushali
 *
 */
public class UpdateRequestHandler implements AddressBookRequestHandler {
	/** address book data service object */
	private AddressBookDataService dataService;

	/** address book response object */
	private AddressBookResponse response = null;

	/** List of existing person entity objects */
	private List<PersonEntity> personEntities = null;

	/** flag to track internal error */
	private boolean isError = false;

	public UpdateRequestHandler(AddressBookDataService dataService) {
		this.dataService = dataService;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handleRequest(AddressBookRequest request) {
		response = new AddressBookResponse();

		// fetch record for the provided person record identifier
		PersonDetails personDetails = request.getPersonDetails().get(0);

		List<UUID> ids = new ArrayList<>();
		ids.add(personDetails.getId());

		try {
			personEntities = dataService.getRecordById(ids);
		} catch (AddresBookException e) {
			e.printStackTrace();
			isError = true;
			if (response.getErrorMessages() == null)
				response.setErrorMessages(new ArrayList<ErrorMessages>());
			response.getErrorMessages().add(new ErrorMessages(ErrorMessageEnum.INTERNAL_SERVER_ERROR, null));
		}

		// handle not existing error
		if (personEntities == null || personEntities.isEmpty()) {
			if (response.getErrorMessages() == null)
				response.setErrorMessages(new ArrayList<ErrorMessages>());
			response.getErrorMessages().add(
					new ErrorMessages(ErrorMessageEnum.RECORD_NOT_FOUND_TO_PERSIST, personDetails.getId().toString()));
		} else {
			// map the updated values from request to persistence object
			PersonEntity personEntity = personEntities.get(0);

			updatePersonEntityBasedOnRequest(personEntity, personDetails);

			// merge the changed database object
			try {
				PersonEntity updatedPersonEntity = dataService.saveOrUpdateRecord(personEntity);

				if (updatedPersonEntity != null) {
					PersonDetails updatedPersonDetails = AddressBookAdapter.convertToPersonDetails(updatedPersonEntity);
					List<PersonDetails> personDetailList = new ArrayList<>();
					personDetailList.add(updatedPersonDetails);
					response.setPersonDetails(personDetailList);
				}
			} catch (AddresBookException e) {
				e.printStackTrace();
				isError = true;
				if (response.getErrorMessages() == null)
					response.setErrorMessages(new ArrayList<ErrorMessages>());
				response.getErrorMessages().add(
						new ErrorMessages(ErrorMessageEnum.RECORD_PERSIST_ERROR, personDetails.getId().toString()));
			}
		}
	}

	/**
	 * Maps request object details to the entity object
	 * 
	 * @param personEntity
	 *            {@link PersonEntity}
	 * @param personDetails
	 *            {@link PersonDetails}
	 */
	private void updatePersonEntityBasedOnRequest(PersonEntity personEntity, PersonDetails personDetails) {
		// maps address details entity
		if (personDetails.getAddressDetails() != null && !personDetails.getAddressDetails().isEmpty()) {
			Map<UUID, AddressDetails> addressDetailsMap = new HashMap<>();
			for (AddressDetails addressDetails : personDetails.getAddressDetails()) {
				addressDetailsMap.put(addressDetails.getId(), addressDetails);
			}

			if (personEntity.getAddressDetails() != null && !personEntity.getAddressDetails().isEmpty()) {
				Iterator<AddressEntity> iterator = personEntity.getAddressDetails().iterator();
				AddressEntity addressEntity;
				AddressDetails addressDetails;
				while (iterator.hasNext()) {
					addressEntity = iterator.next();
					if (addressDetailsMap.containsKey(addressEntity.getId())) {
						addressDetails = addressDetailsMap.get(addressEntity.getId());

						addressEntity.setApartmentNumber(addressDetails.getApartmentNumber());
						addressEntity.setCity(addressDetails.getCity());
						addressEntity.setCountry(addressDetails.getCountry());
						addressEntity.setState(addressDetails.getState());
						addressEntity.setZipCode(addressDetails.getZipCode());
						addressEntity.setStreetAddress(addressDetails.getStreetAddress());
					}
				}
			}
		}

		// map contact number details
		if (personDetails.getContactNumberDetails() != null && !personDetails.getContactNumberDetails().isEmpty()) {
			Map<UUID, ContactNumberDetails> contactNumberDetailsMap = new HashMap<>();
			for (ContactNumberDetails contactNumberDetails : personDetails.getContactNumberDetails()) {
				contactNumberDetailsMap.put(contactNumberDetails.getId(), contactNumberDetails);
			}

			if (personEntity.getContactNumberDetails() != null && !personEntity.getContactNumberDetails().isEmpty()) {
				Iterator<ContactNumberEntity> iterator = personEntity.getContactNumberDetails().iterator();
				ContactNumberEntity contactNumberEntity;
				ContactNumberDetails contactNumberDetails;
				while (iterator.hasNext()) {
					contactNumberEntity = iterator.next();
					if (contactNumberDetailsMap.containsKey(contactNumberEntity.getId())) {
						contactNumberDetails = contactNumberDetailsMap.get(contactNumberEntity.getId());

						contactNumberEntity.setContactNumber(contactNumberDetails.getContactNumber());
					}
				}
			}
		}

		// map email address details
		if (personDetails.getEmailAddressDetails() != null && !personDetails.getEmailAddressDetails().isEmpty()) {
			Map<UUID, EmailAddressDetails> emailAddressDetailsMap = new HashMap<>();
			for (EmailAddressDetails emailAddressDetails : personDetails.getEmailAddressDetails()) {
				emailAddressDetailsMap.put(emailAddressDetails.getId(), emailAddressDetails);
			}

			if (personEntity.getEmailAddressDetails() != null && !personEntity.getEmailAddressDetails().isEmpty()) {
				Iterator<EmailAddressEntity> iterator = personEntity.getEmailAddressDetails().iterator();
				EmailAddressEntity emailAddressEntity;
				EmailAddressDetails emailAddressDetails;
				while (iterator.hasNext()) {
					emailAddressEntity = iterator.next();
					if (emailAddressDetailsMap.containsKey(emailAddressEntity.getId())) {
						emailAddressDetails = emailAddressDetailsMap.get(emailAddressEntity.getId());

						emailAddressEntity.setEmailAddress(emailAddressDetails.getEmailAddress());
					}
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Response buildResponse() {
		if (response != null && response.getErrorMessages() == null) {
			return Response.status(Status.OK).entity(response).build();
		} else if(isError){
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(response).build();
		} else if (personEntities == null || personEntities.isEmpty()) {
			return Response.status(Status.NO_CONTENT).build();
		} else
			return null;
			
	}
}
