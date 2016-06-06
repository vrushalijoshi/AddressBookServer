package com.addressbook.service.request;

import java.util.List;
import java.util.UUID;

import com.addressbook.enums.RequestType;
import com.addressbook.response.model.PersonDetails;

/**
 * Address book request object
 * 
 * @author Vrushali
 *
 */
public class AddressBookRequest {
	/** Request type used to get validator and handler */
	private RequestType requestType;

	/** list of {@link PersonDetails} object to be created or updated */
	private List<PersonDetails> personDetails;

	/** list of ids to fetch records or delete records for */
	private List<UUID> ids;

	/** maintains the query parameters */
	private QueryParams queryParams;

	private AddressBookRequest(AddressBookRequestBuilder addressBookRequestBuilder) {
		this.requestType = addressBookRequestBuilder.requestType;
		this.personDetails = addressBookRequestBuilder.personDetails;
		this.ids = addressBookRequestBuilder.ids;
		this.queryParams = addressBookRequestBuilder.queryParams;
	}

	public static class AddressBookRequestBuilder {

		private RequestType requestType;
		private List<PersonDetails> personDetails;
		private List<UUID> ids;
		private QueryParams queryParams;

		public AddressBookRequest build() {
			return new AddressBookRequest(this);
		}

		/**
		 * @param requestType
		 *            the requestType to set
		 */
		public AddressBookRequestBuilder setRequestType(RequestType requestType) {
			this.requestType = requestType;
			return this;
		}

		/**
		 * @param personDetails
		 *            the personDetails to set
		 */
		public AddressBookRequestBuilder setPersonDetails(List<PersonDetails> personDetails) {
			this.personDetails = personDetails;
			return this;
		}

		/**
		 * @param ids
		 *            the ids to set
		 */
		public AddressBookRequestBuilder setIds(List<UUID> ids) {
			this.ids = ids;
			return this;
		}

		/**
		 * @param queryParams
		 *            the queryParams to set
		 */
		public AddressBookRequestBuilder setQueryParams(QueryParams queryParams) {
			this.queryParams = queryParams;
			return this;
		}
	}

	/**
	 * @return the requestType
	 */
	public RequestType getRequestType() {
		return requestType;
	}

	/**
	 * @return the personDetails
	 */
	public List<PersonDetails> getPersonDetails() {
		return personDetails;
	}

	/**
	 * @return the ids
	 */
	public List<UUID> getIds() {
		return ids;
	}

	/**
	 * @return the queryParams
	 */
	public QueryParams getQueryParams() {
		return queryParams;
	}
}
