package com.addressbook.adaptor;

import java.util.ArrayList;
import java.util.List;

import com.addressbook.persist.model.AddressEntity;
import com.addressbook.persist.model.ContactNumberEntity;
import com.addressbook.persist.model.EmailAddressEntity;
import com.addressbook.persist.model.PersonEntity;
import com.addressbook.response.model.AddressDetails;
import com.addressbook.response.model.ContactNumberDetails;
import com.addressbook.response.model.EmailAddressDetails;
import com.addressbook.response.model.PersonDetails;

/**
 * Adapter class for converting model object to corresponding persistence object
 * and vise versa
 * 
 * @author Vrushali
 *
 */
public class AddressBookAdapter {
	/**
	 * Converts list of person model object to list of corresponding persistence
	 * object
	 * 
	 * @param personDetails
	 *            {@link PersonDetails}
	 * @return {@link PersonEntity}
	 */
	public static PersonEntity convertToPersonEntity(PersonDetails personDetails) {
		if (personDetails == null)
			return null;

		PersonEntity personEntity = new PersonEntity();
		personEntity.setId(personDetails.getId());
		personEntity.setLastName(personDetails.getLastName());
		personEntity.setFirstName(personDetails.getFirstName());
		personEntity.setMiddleName(personDetails.getMiddleName());
		personEntity.setDateOfBirth(personDetails.getDateOfBirth());

		personEntity.setAddressDetails(convertToAddressEntities(personDetails.getAddressDetails()));
		personEntity.setContactNumberDetails(convertToContactNumberEntities(personDetails.getContactNumberDetails()));
		personEntity.setEmailAddressDetails(convertToEmailAddressEntities(personDetails.getEmailAddressDetails()));
		return personEntity;
	}

	/**
	 * Converts list of address details model object to list of corresponding
	 * persistence object
	 * 
	 * @param addressDetails
	 *            {@link List<AddressDetails>}
	 * @return {@link List<AddressEntity>}
	 */
	public static List<AddressEntity> convertToAddressEntities(List<AddressDetails> addressDetails) {
		if (addressDetails == null || (addressDetails != null && addressDetails.isEmpty()))
			return null;
		List<AddressEntity> addressEntities = new ArrayList<AddressEntity>();
		for (AddressDetails addressDetail : addressDetails) {
			AddressEntity addressEntity = convertToAddressEntity(addressDetail);
			addressEntities.add(addressEntity);
		}

		return addressEntities;
	}

	/**
	 * converts address details model object to corresponding persistence object
	 * 
	 * @param addressDetails
	 *            {@link AddressDetails}
	 * @return {@link AddressEntity}
	 */
	public static AddressEntity convertToAddressEntity(AddressDetails addressDetails) {
		if (addressDetails == null)
			return null;

		AddressEntity addressEntity = new AddressEntity();
		addressEntity.setId(addressDetails.getId());
		addressEntity.setApartmentNumber(addressDetails.getApartmentNumber());
		addressEntity.setCity(addressDetails.getCity());
		addressEntity.setCountry(addressDetails.getCountry());
		addressEntity.setState(addressDetails.getState());
		addressEntity.setZipCode(addressDetails.getZipCode());
		addressEntity.setStreetAddress(addressDetails.getStreetAddress());
		return addressEntity;
	}

	/**
	 * Converts list of contact number model objects to list of corresponding
	 * persistence objects
	 * 
	 * @param contactNumberDetails
	 *            {@link List<ContactNumberDetails>}
	 * @return {@link List<ContactNumberEntity>}
	 */
	public static List<ContactNumberEntity> convertToContactNumberEntities(
			List<ContactNumberDetails> contactNumberDetails) {
		if (contactNumberDetails == null || (contactNumberDetails != null && contactNumberDetails.isEmpty()))
			return null;
		List<ContactNumberEntity> contactNumberEntities = new ArrayList<ContactNumberEntity>();
		for (ContactNumberDetails contactNumberDetail : contactNumberDetails) {
			ContactNumberEntity contactNumberEntity = convertToContactNumberEntity(contactNumberDetail);
			contactNumberEntities.add(contactNumberEntity);
		}

		return contactNumberEntities;
	}

	/**
	 * Converts contact number model object to corresponding persistence object
	 * 
	 * @param contactNumbreDetails
	 *            {@link ContactNumberDetails}
	 * @return {@link ContactNumberEntity}
	 */
	public static ContactNumberEntity convertToContactNumberEntity(ContactNumberDetails contactNumbreDetails) {
		if (contactNumbreDetails == null)
			return null;

		ContactNumberEntity contactNumberEntity = new ContactNumberEntity();
		contactNumberEntity.setId(contactNumbreDetails.getId());
		contactNumberEntity.setContactNumber(contactNumbreDetails.getContactNumber());
		return contactNumberEntity;
	}

	/**
	 * converts list of email address model object to list of corresponding
	 * persistence objects persistence entity
	 * 
	 * @param emailAddressDetails
	 *            {@link List<EmailAddressDetails>}
	 * @return {@link List<EmailAddressEntity>}
	 */
	public static List<EmailAddressEntity> convertToEmailAddressEntities(
			List<EmailAddressDetails> emailAddressDetails) {
		if (emailAddressDetails == null || (emailAddressDetails != null && emailAddressDetails.isEmpty()))
			return null;
		List<EmailAddressEntity> emailAddressEntities = new ArrayList<EmailAddressEntity>();
		for (EmailAddressDetails emailAddressDeatil : emailAddressDetails) {
			EmailAddressEntity emailAddressEntity = convertToEmailAddressEntity(emailAddressDeatil);
			emailAddressEntities.add(emailAddressEntity);
		}

		return emailAddressEntities;
	}

	/**
	 * Converts email address model object to corresponding persistence object
	 * 
	 * @param emailAddressDetail
	 *            {@link EmailAddressDetails}
	 * @return {@link EmailAddressEntity}
	 */
	public static EmailAddressEntity convertToEmailAddressEntity(EmailAddressDetails emailAddressDetail) {
		if (emailAddressDetail == null)
			return null;

		EmailAddressEntity emailAddressEntity = new EmailAddressEntity();
		emailAddressEntity.setId(emailAddressDetail.getId());
		emailAddressEntity.setEmailAddress(emailAddressDetail.getEmailAddress());
		return emailAddressEntity;
	}

	/**
	 * Converts list of person persistence entities to list of corresponding
	 * model objects
	 * 
	 * @param personEntities
	 *            {@link List<PersonEntity>}
	 * @return {@link List<PersonDetails>}
	 */
	public static List<PersonDetails> convertToPersonDetailsList(List<PersonEntity> personEntities) {
		if (personEntities == null || (personEntities != null && personEntities.isEmpty()))
			return null;

		List<PersonDetails> personDetails = new ArrayList<PersonDetails>();
		for (PersonEntity personEntity : personEntities) {
			PersonDetails personDetail = convertToPersonDetails(personEntity);
			personDetails.add(personDetail);
		}

		return personDetails;
	}

	/**
	 * Converts person entity to corresponding model object
	 * 
	 * @param personEntity
	 *            {@link PersonEntity}
	 * @return {@link PersonDetails}
	 */
	public static PersonDetails convertToPersonDetails(PersonEntity personEntity) {
		if (personEntity == null)
			return null;

		PersonDetails personDetails = new PersonDetails();
		personDetails.setId(personEntity.getId());
		personDetails.setLastName(personEntity.getLastName());
		personDetails.setFirstName(personEntity.getFirstName());
		personDetails.setMiddleName(personEntity.getMiddleName());
		personDetails.setDateOfBirth(personEntity.getDateOfBirth());
		personDetails.setLastModifiedDate(personEntity.getLastModifiedDate());

		personDetails.setAddressDetails(convertToAddressDetails(personEntity.getAddressDetails()));
		personDetails.setContactNumberDetails(convertToContactNumberDetails(personEntity.getContactNumberDetails()));
		personDetails.setEmailAddressDetails(convertToEmailAddressDetails(personEntity.getEmailAddressDetails()));
		return personDetails;
	}

	/**
	 * Converts list of address details entity objects to list of corresponding
	 * model objects
	 * 
	 * @param addressDetails
	 *            {@link List<AddressEntity>}
	 * @return {@link List<AddressDetails>}
	 */
	public static List<AddressDetails> convertToAddressDetails(List<AddressEntity> addressDetails) {
		if (addressDetails == null || (addressDetails != null && addressDetails.isEmpty()))
			return null;
		List<AddressDetails> addressEntities = new ArrayList<AddressDetails>();
		for (AddressEntity addressDetail : addressDetails) {
			AddressDetails addressEntity = convertToAddressDetails(addressDetail);
			addressEntities.add(addressEntity);
		}

		return addressEntities;
	}

	/**
	 * Converts address details entity object to corresponding model object
	 * 
	 * @param addressEntity
	 *            {@link AddressEntity}
	 * @return {@link AddressDetails}
	 */
	public static AddressDetails convertToAddressDetails(AddressEntity addressEntity) {
		if (addressEntity == null)
			return null;

		AddressDetails addressDetails = new AddressDetails();
		addressDetails.setId(addressEntity.getId());
		addressDetails.setApartmentNumber(addressEntity.getApartmentNumber());
		addressDetails.setCity(addressEntity.getCity());
		addressDetails.setCountry(addressEntity.getCountry());
		addressDetails.setState(addressEntity.getState());
		addressDetails.setZipCode(addressEntity.getZipCode());
		addressDetails.setStreetAddress(addressEntity.getStreetAddress());
		addressDetails.setLastModifiedDate(addressEntity.getLastModifiedDate());
		return addressDetails;
	}

	/**
	 * Converts list of contact number detail persistence objects to list of
	 * corresponding model objects
	 * 
	 * @param contactNumberDetails
	 *            {@link List<ContactNumberEntity>}
	 * @return {@link List<ContactNumberDetails>}
	 */
	public static List<ContactNumberDetails> convertToContactNumberDetails(
			List<ContactNumberEntity> contactNumberDetails) {
		if (contactNumberDetails == null || (contactNumberDetails != null && contactNumberDetails.isEmpty()))
			return null;
		List<ContactNumberDetails> contactNumberEntities = new ArrayList<ContactNumberDetails>();
		for (ContactNumberEntity contactNumberDetail : contactNumberDetails) {
			ContactNumberDetails contactNumberEntity = convertToContactDetails(contactNumberDetail);
			contactNumberEntities.add(contactNumberEntity);
		}

		return contactNumberEntities;
	}

	/**
	 * Converts contact number detail persistence object to corresponding model
	 * object
	 * 
	 * @param contactNumberDetails
	 *            {@link ContactNumberEntity}
	 * @return {@link ContactNumberDetails}
	 */
	public static ContactNumberDetails convertToContactDetails(ContactNumberEntity contactNumbreDetails) {
		if (contactNumbreDetails == null)
			return null;

		ContactNumberDetails contactNumberEntity = new ContactNumberDetails();
		contactNumberEntity.setId(contactNumbreDetails.getId());
		contactNumberEntity.setContactNumber(contactNumbreDetails.getContactNumber());
		contactNumberEntity.setLastModifiedDate(contactNumbreDetails.getLastModifiedDate());
		return contactNumberEntity;
	}

	/**
	 * Converts list of email address details persistence objects to list of
	 * corresponding model objects
	 * 
	 * @param emailAddressDetails
	 *            {@link List<EmailAddressEntity>}
	 * @return {@link List<EmailAddressDetails>}
	 */
	public static List<EmailAddressDetails> convertToEmailAddressDetails(List<EmailAddressEntity> emailAddressDetails) {
		if (emailAddressDetails == null || (emailAddressDetails != null && emailAddressDetails.isEmpty()))
			return null;
		List<EmailAddressDetails> emailAddressEntities = new ArrayList<EmailAddressDetails>();
		for (EmailAddressEntity emailAddressDeatil : emailAddressDetails) {
			EmailAddressDetails emailAddressEntity = convertToEmailAddressDetails(emailAddressDeatil);
			emailAddressEntities.add(emailAddressEntity);
		}

		return emailAddressEntities;
	}

	/**
	 * Converts the email address details persistence object to corresponding
	 * email address deatils model object
	 * 
	 * @param emailAddressDetail
	 *            {@link EmailAddressEntity}
	 * @return {@link EmailAddressDetails}
	 */
	public static EmailAddressDetails convertToEmailAddressDetails(EmailAddressEntity emailAddressDetail) {
		if (emailAddressDetail == null)
			return null;

		EmailAddressDetails emailAddressEntity = new EmailAddressDetails();
		emailAddressEntity.setId(emailAddressDetail.getId());
		emailAddressEntity.setEmailAddress(emailAddressDetail.getEmailAddress());
		emailAddressEntity.setLastModifiedDate(emailAddressDetail.getLastModifiedDate());
		return emailAddressEntity;
	}
}
