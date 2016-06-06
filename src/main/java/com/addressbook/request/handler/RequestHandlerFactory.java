package com.addressbook.request.handler;

import com.addressbook.dataservice.AddressBookDataService;
import com.addressbook.enums.RequestType;

/**
 * Factory to get request handler corresponding to the provided request type
 * 
 * @author Vrushali
 *
 */
public class RequestHandlerFactory {
	public static AddressBookRequestHandler getRequestHandler(RequestType requestType) {
		AddressBookRequestHandler handler = null;
		AddressBookDataService dataService = new AddressBookDataService();
		if (RequestType.CREATE_ADDRESS_BOOK_RECORD_REQUEST.equals(requestType)) {
			handler = new CreateRequestHandler(dataService);
		} else if (RequestType.UPDATE_ADDRESS_BOOK_RECORD_REQUEST.equals(requestType)) {
			handler = new UpdateRequestHandler(dataService);
		} else if (RequestType.GET_ADDRESS_BOOK_RECORD_REQUEST.equals(requestType)) {
			handler = new GetRequestHandler(dataService);
		} else if (RequestType.DELETE_ADDRESS_BOOK_RECORD_REQUEST.equals(requestType)) {
			handler = new DeleteRequestHandler(dataService);
		}

		return handler;
	}
}
