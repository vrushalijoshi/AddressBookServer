package com.addressbook.dataservice;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import org.joda.time.DateTime;

import com.addressbook.exception.AddresBookException;
import com.addressbook.listener.PersistenceListener;
import com.addressbook.persist.model.AddressEntity;
import com.addressbook.persist.model.ContactNumberEntity;
import com.addressbook.persist.model.EmailAddressEntity;
import com.addressbook.persist.model.PersonEntity;
import com.addressbook.service.response.ErrorMessageEnum;

/**
 * Data service to interact with database
 * 
 * @author Vrushali
 *
 */
public class AddressBookDataService {

	private EntityManager em;

	public AddressBookDataService() {
		em = PersistenceListener.getEntityManager();
	}

	/**
	 * Creates a new record or updates an existing record
	 * 
	 * @param personEntity
	 *            {@link PersonEntity} person entity to update/save
	 * @return {@link PersonEntity} updated/saved copy of person entity
	 * @throws AddresBookException
	 *             throws {@link AddresBookException} in case of failure while
	 *             persisting a record
	 */
	public PersonEntity saveOrUpdateRecord(PersonEntity personEntity) throws AddresBookException {
		try {
			em.getTransaction().begin();

			assignIdToNewRecords(personEntity);

			if (personEntity.getId() == null) {
				personEntity.setId(UUID.randomUUID());
				try {
					em.persist(personEntity);
					em.flush();
					em.refresh(personEntity);
				} catch (EntityExistsException entityExistsException) {
					entityExistsException.printStackTrace();
					//throws exception in case record already exists
					throw new AddresBookException(ErrorMessageEnum.RECORD_ALREADY_PRESENT,
							entityExistsException.getCause());
				} catch (EntityNotFoundException entityNotFoundException) {
					entityNotFoundException.printStackTrace();
					//throws exception in case record is not found
					throw new AddresBookException(ErrorMessageEnum.RECORD_NOT_FOUND_TO_PERSIST,
							entityNotFoundException.getCause());
				} catch (PersistenceException persistenceException) {
					persistenceException.printStackTrace();
					//throws exception in case of failure while persisting the data
					throw new AddresBookException(ErrorMessageEnum.RECORD_PERSIST_ERROR,
							persistenceException.getCause());
				}
			} else {
				em.merge(personEntity);
			}

			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw new AddresBookException(ErrorMessageEnum.RECORD_PERSIST_ERROR, e.getCause());
		}
		return personEntity;
	}

	/**
	 * Assigns unique identifier for records with null id
	 * 
	 * @param personEntity
	 *            {@link PersonEntity} person entity to update
	 */
	private void assignIdToNewRecords(PersonEntity personEntity) {
		//assign identifier for address records
		if (personEntity.getAddressDetails() != null) {
			for (AddressEntity addressEntity : personEntity.getAddressDetails()) {
				if (addressEntity.getId() == null)
					addressEntity.setId(UUID.randomUUID());
			}
		}

		//assign identifier for contact number record
		if (personEntity.getContactNumberDetails() != null) {
			for (ContactNumberEntity contactNumberEntity : personEntity.getContactNumberDetails()) {
				if (contactNumberEntity.getId() == null)
					contactNumberEntity.setId(UUID.randomUUID());
			}
		}

		//assign identifer for email address record
		if (personEntity.getEmailAddressDetails() != null) {
			for (EmailAddressEntity emailAddressEntity : personEntity.getEmailAddressDetails()) {
				if (emailAddressEntity.getId() == null)
					emailAddressEntity.setId(UUID.randomUUID());
			}
		}
	}

	/**
	 * Get person entity record for the provided identifers
	 * 
	 * @param ids
	 *            list of record identifiers 
	 * @return {@link List<PersonEntity>} list of person entities
	 * @throws AddresBookException
	 *             throws {@link AddresBookException} in case of failure while
	 *             fetching the records
	 */
	public List<PersonEntity> getRecordById(List<UUID> ids) throws AddresBookException {
		List<PersonEntity> personEntities = null;
		try {
			em.getTransaction().begin();
			TypedQuery<PersonEntity> query = em.createNamedQuery(PersonEntity.GET_RECORD_BY_ID_QUERY_NAME,
					PersonEntity.class);
			query.setParameter("ids", ids);
			personEntities = query.getResultList();
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw new AddresBookException(ErrorMessageEnum.RECORD_FETCH_ERROR, e.getCause());
		}
		return personEntities;
	}
	
	/**
	 * Get person entity record for the provided name and date of birth
	 * 
	 * 
	 * @throws AddresBookException
	 *             throws {@link AddresBookException} in case of failure while
	 *             fetching the records
	 */
	public List<PersonEntity> getRecordByName(String firstName, String lastName, String middleName, DateTime dateOfBirth) throws AddresBookException {
		List<PersonEntity> personEntities = null;
		try {
			em.getTransaction().begin();
			TypedQuery<PersonEntity> query = em.createNamedQuery(PersonEntity.GET_RECORD_BY_NAME_DOB_QUERY_NAME,
					PersonEntity.class);
			query.setParameter("firstName", firstName);
			query.setParameter("lastName", lastName);
			query.setParameter("middleName", middleName);
			query.setParameter("dateOfBirth", dateOfBirth);
			
			personEntities = query.getResultList();
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw new AddresBookException(ErrorMessageEnum.RECORD_FETCH_BY_NAME_ERROR, e.getCause());
		}
		return personEntities;
	}

	/**
	 * Deletes a record based on the provided id
	 * 
	 * @param personEntities
	 *            {@link List<PersonEntity>} list of person entity
	 * @throws AddresBookException
	 *             throws {@link AddresBookException} in case of failure while
	 *             deleting a record
	 */
	public void deleteRecordById(List<PersonEntity> personEntities) throws AddresBookException {
		try {
			em.getTransaction().begin();
			for (PersonEntity personEntity : personEntities) {
				em.remove(personEntity);
			}
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw new AddresBookException(ErrorMessageEnum.RECORD_DELETE_ERROR, e.getCause());
		}
	}
}
