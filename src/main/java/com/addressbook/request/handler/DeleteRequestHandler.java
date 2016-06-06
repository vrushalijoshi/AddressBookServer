package com.addressbook.request.handler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.addressbook.dataservice.AddressBookDataService;
import com.addressbook.persist.model.PersonEntity;
import com.addressbook.service.request.AddressBookRequest;
import com.addressbook.service.response.AddressBookResponse;
import com.addressbook.service.response.ErrorMessageEnum;
import com.addressbook.service.response.ErrorMessages;
import com.addressbook.util.AddressBookUtility;

/**
 * {@link AddressBookRequestHandler} implementation to handle delete request.
 * Deletes records based on provided id.
 * 
 * @author Vrushali
 *
 */
public class DeleteRequestHandler implements AddressBookRequestHandler {

	/** Address book data service object */
	private AddressBookDataService dataService;

	/** Address book response object */
	private AddressBookResponse response = null;

	/** list of ids not found while processing the request */
	private Set<UUID> notFoundIds = null;

	public DeleteRequestHandler(AddressBookDataService dataService) {
		this.dataService = dataService;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handleRequest(AddressBookRequest request) {
		response = new AddressBookResponse();
		try {
			// fetch records to be deleted
			List<PersonEntity> personEntities = dataService.getRecordById(request.getIds());

			// get list of ids not found
			if (personEntities == null
					|| (personEntities != null && personEntities.size() != request.getIds().size())) {
				notFoundIds = getMissingIds(personEntities, request.getIds());
			}

			// update error messages if missing ids exists
			if (notFoundIds != null && !notFoundIds.isEmpty()) {
				ErrorMessages errorMessages = new ErrorMessages(ErrorMessageEnum.RECORD_NOT_FOUND,
						AddressBookUtility.getErrorData(notFoundIds));
				if (response.getErrorMessages() == null)
					response.setErrorMessages(new ArrayList<ErrorMessages>());
				response.getErrorMessages().add(errorMessages);
			}

			// deletes records from database
			dataService.deleteRecordById(personEntities);
		} catch (Exception e) {
			
			e.printStackTrace();
			// updates the list of errors messages in case of any exception
			// while processing the request
			ErrorMessages errorMessages = new ErrorMessages(ErrorMessageEnum.RECORD_FETCH_ERROR, "");
			if (response.getErrorMessages() == null)
				response.setErrorMessages(new ArrayList<ErrorMessages>());
			response.getErrorMessages().add(errorMessages);
		}
	}

	/**
	 * Returns set of ids not found while deleting the records.
	 * 
	 * @param personEntities
	 *            {@link List<PersonEntity>} list of person entities found
	 * @param ids
	 *            {@link List<UUID>} list of ids to delete
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
		if (response != null && response.getErrorMessages() == null) {
			return Response.status(Status.OK).build();
		} else if (notFoundIds != null && !notFoundIds.isEmpty()) {
			return Response.status(Status.NO_CONTENT).build();
		} else {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(response).build();
		}
	}
}
