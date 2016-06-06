package com.addressbook.enums;

/**
 * Maintains request types
 * 
 * @author Vrushali
 *
 */
public enum RequestType {
	/** type for create record request */
	CREATE_ADDRESS_BOOK_RECORD_REQUEST("createAddressBookRecord"), 
	/** type for update record request */
	UPDATE_ADDRESS_BOOK_RECORD_REQUEST("updateAddressBookRecord"), 
   /** type for get record request */
	GET_ADDRESS_BOOK_RECORD_REQUEST("getAddressBookRecord"), 
	/** type for delete record request */
	DELETE_ADDRESS_BOOK_RECORD_REQUEST("deleteAddressBookRecord");

	private String requestType;

	private RequestType(String requestType) {
		this.requestType = requestType;
	}

	/**
	 * @return the requestType
	 */
	public String getRequestType() {
		return requestType;
	}
}
