package com.addressbook.dataservice;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.addressbook.dataservice.AddressBookDataService;
import com.addressbook.exception.AddresBookException;
import com.addressbook.listener.PersistenceListener;
import com.addressbook.persist.model.PersonEntity;
import com.addressbook.service.response.ErrorMessageEnum;

/**
 * Test class for testing address book data service methods
 * 
 * @author Vrushali
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class AddressBookDataServiceTest {
	@Mock
	private EntityManager em;

	@Mock
	private EntityManagerFactory emf;

	private AddressBookDataService addressBookDataService;

	@Mock
	private PersistenceListener persistenceListener;

	@Mock
	private EntityTransaction entityTransaction;

	@Mock
	private TypedQuery<PersonEntity> typedQuery;

	@Before
	public void setup() {
		PersistenceListener.setEntityManagerFactory(emf);
		when(emf.createEntityManager()).thenReturn(em);

		when(em.getTransaction()).thenReturn(entityTransaction);

		addressBookDataService = new AddressBookDataService();
	}

	/**
	 * Tests saveOrUpdate method, used to create/update records
	 */
	@Test
	public void saveOrUpdateRecord() {
		PersonEntity personEntity = makePersonEntity();

		try {
			addressBookDataService.saveOrUpdateRecord(personEntity);
		} catch (AddresBookException e) {
			e.printStackTrace();
		}

		verify(em).persist(personEntity);
		verify(em).flush();
		verify(em).refresh(personEntity);

		personEntity.setId(UUID.randomUUID());

		try {
			addressBookDataService.saveOrUpdateRecord(personEntity);
		} catch (AddresBookException e) {
			e.printStackTrace();
		}

		verify(em).merge(personEntity);

		try {
			addressBookDataService.saveOrUpdateRecord(null);
		} catch (AddresBookException e) {
			Assert.assertEquals(ErrorMessageEnum.RECORD_PERSIST_ERROR, e.getErrorMessageEnum());
		}

		verify(em, times(5)).getTransaction();
	}

	/**
	 * Tests getReordById method used to fetch records based on provided ids
	 */
	@Test
	public void getRecordById() {
		List<UUID> uuids = new ArrayList<>();
		uuids.add(UUID.randomUUID());

		when(em.createNamedQuery(PersonEntity.GET_RECORD_BY_ID_QUERY_NAME, PersonEntity.class)).thenReturn(typedQuery);

		when(typedQuery.getResultList()).thenReturn(new ArrayList<PersonEntity>());

		List<PersonEntity> result = null;
		try {
			result = addressBookDataService.getRecordById(uuids);
		} catch (AddresBookException e) {
			e.printStackTrace();
		}

		Assert.assertNotNull(result);

		try {
			addressBookDataService.getRecordById(null);
		} catch (AddresBookException e) {
			Assert.assertEquals(ErrorMessageEnum.RECORD_FETCH_ERROR, e.getErrorMessageEnum());
		}
	}

	/**
	 * Tests the deleteRecordById method used to delete the record form database
	 */
	@Test
	public void deleteRecordById() {
		List<PersonEntity> personEntities = makePersonEntityList();

		try {
			addressBookDataService.deleteRecordById(personEntities);
		} catch (AddresBookException e) {
			e.printStackTrace();
		}

		for (PersonEntity personEntity : personEntities) {
			verify(em).remove(personEntity);
		}

		try {
			addressBookDataService.deleteRecordById(null);
		} catch (AddresBookException e) {
			Assert.assertEquals(ErrorMessageEnum.RECORD_DELETE_ERROR, e.getErrorMessageEnum());
		}

		verify(em, times(3)).getTransaction();
	}

	/**
	 * Builds person entity list
	 * 
	 * @return {@link List<PersonEntity>}
	 */
	private List<PersonEntity> makePersonEntityList() {
		List<PersonEntity> personEntities = new ArrayList<>();

		PersonEntity prEntity = makePersonEntity();
		prEntity.setId(UUID.randomUUID());
		personEntities.add(prEntity);

		prEntity = makePersonEntity();
		prEntity.setId(UUID.randomUUID());
		personEntities.add(prEntity);

		return personEntities;
	}

	/**
	 * Builds person entity object
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
