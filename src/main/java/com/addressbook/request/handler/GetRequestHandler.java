package com.addressbook.request.handler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.addressbook.adaptor.AddressBookAdapter;
import com.addressbook.dataservice.AddressBookDataService;
import com.addressbook.persist.model.PersonEntity;
import com.addressbook.response.model.PersonDetails;
import com.addressbook.service.request.AddressBookRequest;
import com.addressbook.service.response.AddressBookResponse;
import com.addressbook.service.response.ErrorMessageEnum;
import com.addressbook.service.response.ErrorMessages;
import com.addressbook.util.AddressBookUtility;

/**
 * {@link AddressBookRequestHandler} implementation to handle get request.
 * Fetches records corresponding to the provided id.
 * 
 * @author Vrushali
 *
 */
public class GetRequestHandler implements AddressBookRequestHandler {

	/** Address book data service object */
	private AddressBookDataService dataService;

	/** Address book response object */
	private AddressBookResponse response = null;

	/** maintains list of ids for the records not found in database */
	private Set<UUID> notFoundIds = null;

	public GetRequestHandler(AddressBookDataService dataService) {
		this.dataService = dataService;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handleRequest(AddressBookRequest request) {
		response = new AddressBookResponse();

		try {
			// fetch records for the provided identifer from database
			List<PersonEntity> personEntity = dataService.getRecordById(request.getIds());

			// fetch list of ids not found
			if (personEntity == null || (personEntity != null && personEntity.size() != request.getIds().size())) {
				notFoundIds = getMissingIds(personEntity, request.getIds());
			}

			// updates the error message list for missing ids
			if (notFoundIds != null && !notFoundIds.isEmpty()) {
				ErrorMessages errorMessages = new ErrorMessages(ErrorMessageEnum.RECORD_NOT_FOUND,
						AddressBookUtility.getErrorData(notFoundIds));
				if (response.getErrorMessages() == null)
					response.setErrorMessages(new ArrayList<ErrorMessages>());
				response.getErrorMessages().add(errorMessages);
			}

			// convert the list of person entities to corresponding model
			// objects list
			List<PersonDetails> personDetails = AddressBookAdapter.convertToPersonDetailsList(personEntity);

			response.setPersonDetails(personDetails);
		} catch (Exception e) {
			e.printStackTrace();
			// updates the list of error meesages
			ErrorMessages errorMessages = new ErrorMessages(ErrorMessageEnum.RECORD_FETCH_ERROR, "");
			if (response.getErrorMessages() == null)
				response.setErrorMessages(new ArrayList<ErrorMessages>());
			response.getErrorMessages().clear();
			response.getErrorMessages().add(errorMessages);
		}
	}

	/**
	 * Returns set of ids not found while fetching the records.
	 * 
	 * @param personEntities
	 *            {@link List<PersonEntity>} list of person entities found
	 * @param ids
	 *            {@link List<UUID>} list of ids to fetch record for
	 * @return {@link Set<UUID>} set of ids not found
	 */
	private Set<UUID> getMissingIds(List<PersonEntity> personEntities, List<UUID> ids) {
		Set<UUID> notFoundIds = new HashSet<UUID>(ids);

		if (personEntities != null) {
			for (PersonEntity personEntity : personEntities) {
				notFoundIds.remove(personEntity.getId());
			}
		}

		return notFoundIds;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Response buildResponse() {
		if (response != null && (response.getErrorMessages() == null)) {
			return Response.status(Status.OK).entity(response).build();
		} else if (notFoundIds != null && !notFoundIds.isEmpty()) {
			return Response.status(Status.NO_CONTENT).build();
		} else {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(response).build();
		}
	}
}
