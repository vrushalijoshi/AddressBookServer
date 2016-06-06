package com.addressbook.request.test.handler;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.addressbook.dataservice.AddressBookDataService;
import com.addressbook.enums.RequestType;
import com.addressbook.exception.AddresBookException;
import com.addressbook.persist.model.PersonEntity;
import com.addressbook.request.handler.AddressBookRequestHandler;
import com.addressbook.request.handler.GetRequestHandler;
import com.addressbook.response.model.PersonDetails;
import com.addressbook.service.request.AddressBookRequest;
import com.addressbook.service.response.AddressBookResponse;
import com.addressbook.service.response.ErrorMessageEnum;

/**
 * Test class for testing get request handler class
 * 
 * @author Vrushali
 *
 */
@RunWith(value = MockitoJUnitRunner.class)
public class GetRequestHandlerTest {

	@Mock
	private AddressBookDataService dataService;
	AddressBookRequestHandler handler = null;

	@Before
	public void setup() {
		handler = new GetRequestHandler(dataService);
	}

	/**
	 * Performs success test for testing get request handler method
	 */
	@Test
	public void handleRequestSuccessTest() {
		AddressBookRequest request = makeGetRecordRequest();

		List<PersonEntity> personEntities = makePersonEntityList();

		try {
			when(dataService.getRecordById(request.getIds())).thenReturn(personEntities);
		} catch (AddresBookException e) {
			e.printStackTrace();
		}

		handler.handleRequest(request);

		Response response = handler.buildResponse();

		Assert.assertEquals(response.getStatus(), Status.OK.getStatusCode());
		Assert.assertNotNull(response.getEntity());
		Assert.assertEquals(((AddressBookResponse) response.getEntity()).getPersonDetails().size(), 1);

		List<PersonDetails> personDetails = ((AddressBookResponse) response.getEntity()).getPersonDetails();

		PersonEntity expectedPersonEntity = personEntities.get(0);
		PersonDetails actualPersonDetails = personDetails.get(0);

		Assert.assertEquals(expectedPersonEntity.getId(), actualPersonDetails.getId());
	}

	/**
	 * Performs failure test for testing scenario when record is not found
	 */
	@Test
	public void handleRequestNoRecordFoundTest() {
		AddressBookRequest request = makeGetRecordRequest();

		List<PersonEntity> personEntities = null;

		try {
			when(dataService.getRecordById(request.getIds())).thenReturn(personEntities);
		} catch (AddresBookException e) {
			e.printStackTrace();
		}

		handler.handleRequest(request);

		Response response = handler.buildResponse();

		Assert.assertEquals(response.getStatus(), Status.NO_CONTENT.getStatusCode());
		Assert.assertNull(response.getEntity());
	}

	/**
	 * Performs failure test when any exception occurs while processing the
	 * request
	 */
	@Test
	public void handleRequestFailureTest() {
		AddressBookRequest request = makeGetRecordRequest();

		try {
			when(dataService.getRecordById(request.getIds())).thenThrow(
					new AddresBookException(ErrorMessageEnum.RECORD_FETCH_ERROR, new Exception().getCause()));
		} catch (AddresBookException e) {
			e.printStackTrace();
		}

		handler.handleRequest(request);

		Response response = handler.buildResponse();

		Assert.assertEquals(response.getStatus(), Status.INTERNAL_SERVER_ERROR.getStatusCode());
		Assert.assertNotNull(response.getEntity());
		Assert.assertNull(((AddressBookResponse) response.getEntity()).getPersonDetails());
		Assert.assertEquals(((AddressBookResponse) response.getEntity()).getErrorMessages().size(), 1);
	}

	/**
	 * Builds {@link AddressBookRequest}
	 * 
	 * @return {@link AddressBookRequest}
	 */
	private AddressBookRequest makeGetRecordRequest() {
		List<UUID> ids = new ArrayList<UUID>();
		ids.add(UUID.randomUUID());

		AddressBookRequest request = new AddressBookRequest.AddressBookRequestBuilder().setIds(ids)
				.setRequestType(RequestType.GET_ADDRESS_BOOK_RECORD_REQUEST).build();
		return request;
	}

	/**
	 * Builds list of {@link PersonEntity} objects
	 * 
	 * @return {@link List<PersonEntity>}
	 */
	private List<PersonEntity> makePersonEntityList() {
		List<PersonEntity> personEntities = new ArrayList<>();

		PersonEntity prEntity = makePersonEntity();
		prEntity.setId(UUID.randomUUID());
		personEntities.add(prEntity);

		return personEntities;
	}

	/**
	 * Builds {@link PersonEntity} object
	 * 
	 * @return {@link PersonEntity}
	 */
	private PersonEntity makePersonEntity() {
		PersonEntity personEntity = new PersonEntity();
		personEntity.setFirstName("SampleFirstName");
		personEntity.setLastName("SampleLastName");
		personEntity.setMiddleName("SampleMiddleName");
		return personEntity;
	}
}
