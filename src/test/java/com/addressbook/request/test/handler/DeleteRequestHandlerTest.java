package com.addressbook.request.test.handler;

import static org.mockito.Mockito.doNothing;
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
import com.addressbook.request.handler.DeleteRequestHandler;
import com.addressbook.service.request.AddressBookRequest;
import com.addressbook.service.response.AddressBookResponse;
import com.addressbook.service.response.ErrorMessageEnum;

/**
 * Test class for testing {@link DeleteRequestHandler} methods
 * 
 * @author Vrushali
 *
 */
@RunWith(value = MockitoJUnitRunner.class)
public class DeleteRequestHandlerTest {

	@Mock
	private AddressBookDataService dataService;
	AddressBookRequestHandler handler = null;

	@Before
	public void setup() {
		handler = new DeleteRequestHandler(dataService);
	}

	/**
	 * Performs success test for delete record method
	 */
	@Test
	public void handleRequestSuccessTest() {
		AddressBookRequest request = makeDeleteRecordRequest();

		List<PersonEntity> personEntities = makePersonEntityList();

		try {
			when(dataService.getRecordById(request.getIds())).thenReturn(personEntities);
			doNothing().when(dataService).deleteRecordById(personEntities);
		} catch (AddresBookException e) {
			e.printStackTrace();
		}

		handler.handleRequest(request);

		Response response = handler.buildResponse();

		Assert.assertEquals(response.getStatus(), Status.OK.getStatusCode());
		Assert.assertNull(response.getEntity());
	}

	/**
	 * Performs failure test when no record is found
	 */
	@Test
	public void handleRequestNoRecordFoundTest() {
		AddressBookRequest request = makeDeleteRecordRequest();

		List<PersonEntity> personEntities = null;

		try {
			when(dataService.getRecordById(request.getIds())).thenReturn(personEntities);
			doNothing().when(dataService).deleteRecordById(personEntities);
		} catch (AddresBookException e) {
			e.printStackTrace();
		}

		handler.handleRequest(request);

		Response response = handler.buildResponse();

		Assert.assertEquals(response.getStatus(), Status.NO_CONTENT.getStatusCode());
		Assert.assertNull(response.getEntity());
	}

	/**
	 * Performs failure test for any exception
	 */
	@Test
	public void handleRequestFailureTest() {
		AddressBookRequest request = makeDeleteRecordRequest();

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
	 * Builds delete record request
	 * @return {@link AddressBookRequest}
	 */
	private AddressBookRequest makeDeleteRecordRequest() {
		List<UUID> ids = new ArrayList<UUID>();
		ids.add(UUID.randomUUID());

		AddressBookRequest request = new AddressBookRequest.AddressBookRequestBuilder().setIds(ids)
				.setRequestType(RequestType.DELETE_ADDRESS_BOOK_RECORD_REQUEST).build();
		return request;
	}

	/**
	 * Builds list of {@link PersonEntity} objects
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
