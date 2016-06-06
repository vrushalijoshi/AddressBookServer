package com.addressbook.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.addressbook.enums.RequestType;
import com.addressbook.request.handler.AddressBookRequestHandler;
import com.addressbook.request.handler.RequestHandlerFactory;
import com.addressbook.requet.validator.AddressBookRequestValidator;
import com.addressbook.requet.validator.RequestValidatorFactory;
import com.addressbook.response.model.PersonDetails;
import com.addressbook.service.request.AddressBookRequest;
import com.addressbook.service.response.AddressBookResponse;
import com.addressbook.service.response.ErrorMessages;

/**
 * Address book application resource
 * 
 * @author Vrushali
 *
 */
@Path("/")
public class AddressBookServiceResource {

	/**
	 * Handles POST request for creating person record in databases
	 * 
	 * @param request
	 *            {@link PersonDetails} person details request object
	 * @return {@link Response}
	 */
	@POST
	@Path("/addAddressBookRecord")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addAddressBookRecord(PersonDetails request) {
		RequestType requestType = RequestType.CREATE_ADDRESS_BOOK_RECORD_REQUEST;

		List<PersonDetails> personDetails = new ArrayList<PersonDetails>();
		personDetails.add(request);

		// build address book request object
		AddressBookRequest addressBookRequest = new AddressBookRequest.AddressBookRequestBuilder()
				.setPersonDetails(personDetails).setRequestType(requestType).build();

		return processRequest(requestType, addressBookRequest);
	}

	/**
	 * Handler for PUT request to update the person record details
	 * 
	 * @param request
	 *            {@link PersonDetails} person details request
	 * @return {@link Response}
	 */
	@PUT
	@Path("/updateContactInformation")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateAddressBookRecord(PersonDetails request) {
		RequestType requestType = RequestType.UPDATE_ADDRESS_BOOK_RECORD_REQUEST;

		List<PersonDetails> personDetails = new ArrayList<PersonDetails>();
		personDetails.add(request);

		// build address book request object
		AddressBookRequest addressBookRequest = new AddressBookRequest.AddressBookRequestBuilder()
				.setPersonDetails(personDetails).setRequestType(requestType).build();

		return processRequest(requestType, addressBookRequest);
	}

	/**
	 * Handler for GET request to fetch records for the provided identifier
	 * 
	 * @param request
	 *            {@link UUID} identifier for fetching corresponding record
	 * @return {@link Response}
	 */
	@GET
	@Path("/getAddressBookRecordById/{uuid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAddressBookRecordById(@PathParam("uuid") UUID uuid) {
		RequestType requestType = RequestType.GET_ADDRESS_BOOK_RECORD_REQUEST;

		List<UUID> ids = new ArrayList<>();
		ids.add(uuid);

		// build address book request object
		AddressBookRequest addressBookRequest = new AddressBookRequest.AddressBookRequestBuilder().setIds(ids)
				.setRequestType(requestType).build();

		return processRequest(requestType, addressBookRequest);
	}

	/**
	 * Handles DELETE request to delete record based on provided identifier
	 * 
	 * @param request
	 *            {@link UUID} identifier to delete record for
	 * @return {@link Response}
	 */
	@DELETE
	@Path("/deleteAddressBookRecordById/{uuid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteAddressBookRecordById(@PathParam("uuid") UUID uuid) {
		RequestType requestType = RequestType.DELETE_ADDRESS_BOOK_RECORD_REQUEST;

		List<UUID> ids = new ArrayList<>();
		ids.add(uuid);

		// build address book request object
		AddressBookRequest addressBookRequest = new AddressBookRequest.AddressBookRequestBuilder().setIds(ids)
				.setRequestType(requestType).build();

		return processRequest(requestType, addressBookRequest);

	}

	/**
	 * Validates and processes the request. If invalid, handler will not be
	 * invoked and response with failure {@link Status} code will be returned If
	 * valid, handler handles the request and return success {@link Status} with
	 * response
	 * 
	 * @param requestType
	 *            {@link RequestType}
	 * @param addressBookRequest
	 *            {@link AddressBookRequest}
	 * @return {@link Response}
	 */
	private Response processRequest(RequestType requestType, AddressBookRequest addressBookRequest) {

		// validate request - if uuid is provided and in correct format
		AddressBookRequestValidator validator = RequestValidatorFactory.getRequestValidator(requestType);
		List<ErrorMessages> errorMessages = validator.validateRequest(addressBookRequest);

		if (errorMessages.isEmpty()) {
			AddressBookRequestHandler handler = RequestHandlerFactory.getRequestHandler(requestType);

			handler.handleRequest(addressBookRequest);
			Response response = handler.buildResponse();

			return response;
		} else {
			AddressBookResponse response = new AddressBookResponse();
			response.setErrorMessages(errorMessages);
			return Response.status(400).entity(response).build();
		}
	}

}
