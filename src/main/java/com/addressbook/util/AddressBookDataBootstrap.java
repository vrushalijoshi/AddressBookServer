package com.addressbook.util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.joda.time.DateTime;

import com.addressbook.persist.model.AddressEntity;
import com.addressbook.persist.model.ContactNumberEntity;
import com.addressbook.persist.model.EmailAddressEntity;
import com.addressbook.persist.model.PersonEntity;

/**
 * Bootstrap utility class used to persist records in database
 * 
 * @author Vrushali
 *
 */
public class AddressBookDataBootstrap {
	/** Entity Manager Object */
	private EntityManager em;

	public void intit() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("addressbook");
		em = emf.createEntityManager();
	}

	public static void main(String[] args) {
		AddressBookDataBootstrap addressBookDataBootstrap = new AddressBookDataBootstrap();
		addressBookDataBootstrap.intit();

		addressBookDataBootstrap.createPerson();
	}

	/**
	 * Persists Person Entity Records
	 */
	private void createPerson() {
		em.getTransaction().begin();
		PersonEntity entity = new PersonEntity();
		entity.setId(UUID.randomUUID());
		entity.setFirstName("Vrushali");
		entity.setLastName("Joshi");
		entity.setMiddleName("Ramdas");

		entity.setDateOfBirth(DateTime.now());

		List<ContactNumberEntity> contactNumbers = new ArrayList<ContactNumberEntity>();

		ContactNumberEntity contactNumberEntity = new ContactNumberEntity();
		contactNumberEntity.setId(UUID.randomUUID());
		contactNumberEntity.setContactNumber(6083385607L);

		contactNumbers.add(contactNumberEntity);
		entity.setContactNumberDetails(contactNumbers);

		List<EmailAddressEntity> emailAddress = new ArrayList<EmailAddressEntity>();

		EmailAddressEntity emailAddressEntity = new EmailAddressEntity();
		emailAddressEntity.setId(UUID.randomUUID());
		emailAddressEntity.setEmailAddress("vrushalijoshi.cummins@gmail.com");
		// emailAddressEntity.setPerson(entity);

		emailAddress.add(emailAddressEntity);
		entity.setEmailAddressDetails(emailAddress);

		List<AddressEntity> address = new ArrayList<AddressEntity>();

		AddressEntity addressEntity = new AddressEntity();
		addressEntity.setId(UUID.randomUUID());
		addressEntity.setApartmentNumber("8222");
		addressEntity.setStreetAddress("9110 Judicial Dr.");
		addressEntity.setCity("San Diego");
		addressEntity.setCountry("United States Of America");
		addressEntity.setState("California");
		addressEntity.setZipCode(92122);

		address.add(addressEntity);
		entity.setAddressDetails(address);

		em.persist(entity);

		em.getTransaction().commit();
	}
}
