package com.addressbook.request.handler;

import javax.ws.rs.core.Response;

import com.addressbook.service.request.AddressBookRequest;

/**
 * Handler interface to process address book request and build http response.
 * 
 * @author Vrushali
 *
 */
public interface AddressBookRequestHandler {
	/**
	 * Handles valid request. Updates error list in case of exception while
	 * processing the request
	 * 
	 * @param request
	 *            {@link AddressBookRequest}
	 */
	public void handleRequest(AddressBookRequest request);

	/**
	 * Builds response based error messages and response data.
	 * 
	 * Error Codes - 200 - OK: Successful; 400 - Bad Request; 204 - Record not
	 * found; 500 - Internal Server Error
	 * 
	 * @return {@link Response}
	 */
	public Response buildResponse();
}
