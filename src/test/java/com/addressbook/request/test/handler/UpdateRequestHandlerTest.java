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
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.addressbook.dataservice.AddressBookDataService;
import com.addressbook.enums.RequestType;
import com.addressbook.exception.AddresBookException;
import com.addressbook.persist.model.PersonEntity;
import com.addressbook.request.handler.AddressBookRequestHandler;
import com.addressbook.request.handler.UpdateRequestHandler;
import com.addressbook.response.model.PersonDetails;
import com.addressbook.service.request.AddressBookRequest;
import com.addressbook.service.response.AddressBookResponse;
import com.addressbook.service.response.ErrorMessageEnum;

/**
 * Test class for testing methods of {@link UpdateRequestHandler}
 * 
 * @author Vrushali
 *
 */
@RunWith(value = MockitoJUnitRunner.class)
public class UpdateRequestHandlerTest {

	@Mock
	private AddressBookDataService dataService;

	AddressBookRequestHandler handler = null;

	@Before
	public void setup() {
		handler = new UpdateRequestHandler(dataService);
	}

	/**
	 * Performs success test for testing update request handler method
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void handleRequestSuccessTest() {
		AddressBookRequest request = makeUpdateRecordRequest();

		List<PersonEntity> personEntities = makePersonEntityList();
		
		try {
			when(dataService.getRecordById(Matchers.anyList())).thenReturn(personEntities);
		} catch (AddresBookException e) {
			e.printStackTrace();
		}
		
		try {
			when(dataService.saveOrUpdateRecord(Matchers.any(PersonEntity.class))).thenReturn(personEntities.get(0));
		} catch (AddresBookException e) {
			e.printStackTrace();
		}

		handler.handleRequest(request);

		Response response = handler.buildResponse();

		Assert.assertEquals(response.getStatus(), Status.OK.getStatusCode());
		Assert.assertNotNull(response.getEntity());
		Assert.assertEquals(((AddressBookResponse) response.getEntity()).getPersonDetails().size(), 1);

		List<PersonDetails> personDetails = ((AddressBookResponse) response.getEntity()).getPersonDetails();

		PersonEntity actualPersonEntity = personEntities.get(0);
		PersonDetails expectedPersonDetails = personDetails.get(0);

		Assert.assertEquals(expectedPersonDetails.getId(), actualPersonEntity.getId());
	}

	/**
	 * Performs failure test for update request handler method
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void handleRequestFailureTest() {
		AddressBookRequest request = makeUpdateRecordRequest();

		List<PersonEntity> personEntities = makePersonEntityList();
		try {
			when(dataService.getRecordById(Matchers.anyList())).thenReturn(personEntities);
		} catch (AddresBookException e) {
			e.printStackTrace();
		}
		
		try {
			when(dataService.saveOrUpdateRecord(Matchers.any(PersonEntity.class))).thenThrow(
					new AddresBookException(ErrorMessageEnum.RECORD_PERSIST_ERROR, new Exception().getCause()));
		} catch (AddresBookException e) {
			e.printStackTrace();
		}

		handler.handleRequest(request);

		Response response = handler.buildResponse();

		Assert.assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
		Assert.assertNotNull(response.getEntity());
		Assert.assertNull(((AddressBookResponse) response.getEntity()).getPersonDetails());
		Assert.assertEquals(((AddressBookResponse) response.getEntity()).getErrorMessages().size(), 1);
	}

	/**
	 * Builds update record request
	 * 
	 * @return {@link AddressBookRequest}
	 */
	private AddressBookRequest makeUpdateRecordRequest() {
		List<PersonDetails> personDetails = makePersonDetails();

		AddressBookRequest request = new AddressBookRequest.AddressBookRequestBuilder().setPersonDetails(personDetails)
				.setRequestType(RequestType.CREATE_ADDRESS_BOOK_RECORD_REQUEST).build();
		return request;
	}

	/**
	 * Builds list of {@link PersonDetails} objects
	 * 
	 * @return {@link List<PersonDetails>}
	 */
	private List<PersonDetails> makePersonDetails() {
		List<PersonDetails> personDetails = new ArrayList<PersonDetails>();
		PersonDetails personDetail = new PersonDetails();
		personDetail.setId(UUID.randomUUID());
		personDetail.setFirstName("SampleFirstName");
		personDetail.setLastName("SampleLastName");
		personDetail.setMiddleName("SampleMiddleName");

		personDetails.add(personDetail);
		return personDetails;
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
