package com.addressbook.request.handler;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.addressbook.adaptor.AddressBookAdapter;
import com.addressbook.dataservice.AddressBookDataService;
import com.addressbook.exception.AddresBookException;
import com.addressbook.persist.model.PersonEntity;
import com.addressbook.response.model.PersonDetails;
import com.addressbook.service.request.AddressBookRequest;
import com.addressbook.service.response.AddressBookResponse;
import com.addressbook.service.response.ErrorMessageEnum;
import com.addressbook.service.response.ErrorMessages;

/**
 * {@link AddressBookRequestHandler} implementation to handle create request.
 * Persists person record based on provided details.
 * 
 * @author Vrushali
 *
 */
public class CreateRequestHandler implements AddressBookRequestHandler {

	/** Address book data service object */
	private AddressBookDataService dataService;

	/** Address book response object */
	private AddressBookResponse response = null;

	/**
	 * List of existing person entities
	 */
	private List<PersonEntity> personEntities = null;

	public CreateRequestHandler(AddressBookDataService dataService) {
		this.dataService = dataService;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handleRequest(AddressBookRequest request) {
		// convert the person model object to corresponding persistence entity
		PersonEntity personEntity = AddressBookAdapter.convertToPersonEntity(request.getPersonDetails().get(0));

		PersonEntity savedPersonEntity = null;
		response = new AddressBookResponse();

		try {
			personEntities = dataService.getRecordByName(personEntity.getFirstName(), personEntity.getLastName(),
					personEntity.getMiddleName(), personEntity.getDateOfBirth());
		} catch (AddresBookException addresBookException) {
			addresBookException.printStackTrace();
			if (response.getErrorMessages() == null) {
				response.setErrorMessages(new ArrayList<ErrorMessages>());
			}
			response.getErrorMessages().add(new ErrorMessages(addresBookException.getErrorMessageEnum(), personEntity.getFirstName()));
		}

		if (personEntities != null && !personEntities.isEmpty()) {
			if (response.getErrorMessages() == null) {
				response.setErrorMessages(new ArrayList<ErrorMessages>());
			}
			response.getErrorMessages()
					.add(new ErrorMessages(ErrorMessageEnum.RECORD_ALREADY_PRESENT, personEntity.getFirstName()));
		} else {

			// save the person entity details
			try {
				savedPersonEntity = dataService.saveOrUpdateRecord(personEntity);
			} catch (AddresBookException e) {
				e.printStackTrace();
				// update list of error meesages
				if (response.getErrorMessages() == null) {
					response.setErrorMessages(new ArrayList<ErrorMessages>());
				}
				response.getErrorMessages().add(new ErrorMessages(e.getErrorMessageEnum(), ""));
			}

			if (savedPersonEntity != null) {
				List<PersonDetails> personDetails = new ArrayList<PersonDetails>();
				PersonDetails personDetail = AddressBookAdapter.convertToPersonDetails(savedPersonEntity);
				personDetails.add(personDetail);
				response.setPersonDetails(personDetails);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Response buildResponse() {
		if (response != null && response.getErrorMessages() == null) {
			return Response.status(Status.CREATED).entity(response).build();
		} else if (personEntities != null && !personEntities.isEmpty()) {
			return Response.status(409).entity(response).build();
		} else
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(response).build();
	}
}
