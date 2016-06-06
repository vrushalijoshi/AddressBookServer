package com.addressbook.persist.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

/**
 * Maintains information corresponding to person entity
 * 
 * @author Vrushali
 *
 */
@Entity
@Table(name = "person")
@NamedQueries({@NamedQuery(name = PersonEntity.GET_RECORD_BY_ID_QUERY_NAME, query = PersonEntity.GET_RECORD_BY_ID_QUERY),
@NamedQuery(name = PersonEntity.GET_RECORD_BY_NAME_DOB_QUERY_NAME, query = PersonEntity.GET_RECORD_BY_NAME_DOB_QUERY)})
public class PersonEntity extends BaseEntity {

	/** query name and query to fetch records by provided ids */
	public static final String GET_RECORD_BY_ID_QUERY_NAME = "getRecordById";
	public static final String GET_RECORD_BY_ID_QUERY = "from PersonEntity where id in (:ids)";
	
	public static final String GET_RECORD_BY_NAME_DOB_QUERY_NAME = "getRecordByNameAndDOB";
	public static final String GET_RECORD_BY_NAME_DOB_QUERY = "from PersonEntity where firstName = :firstName and lastName = :lastName and middleName = :middleName and dateOfBirth = :dateOfBirth";

	/** Version UID for serialization */
	private static final long serialVersionUID = 960786637605611291L;

	/** person's first name */
	@Column(name = "first_name")
	private String firstName;

	/** person's middle name */
	@Column(name = "middle_name")
	private String middleName;

	/** person's last name */
	@Column(name = "last_name")
	private String lastName;

	/** person's date of birth */
	@Column(name = "date_of_birth")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime dateOfBirth;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "person_email_id")
	@Fetch(FetchMode.SELECT)
	private List<EmailAddressEntity> emailAddressDetails;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "person_contact_number_id")
	@Fetch(FetchMode.SELECT)
	private List<ContactNumberEntity> contactNumberDetails;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name = "person_address_id")
	private List<AddressEntity> addressDetails;

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the middleName
	 */
	public String getMiddleName() {
		return middleName;
	}

	/**
	 * @param middleName
	 *            the middleName to set
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the dateOfBirth
	 */
	public DateTime getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * @param dateOfBirth
	 *            the dateOfBirth to set
	 */
	public void setDateOfBirth(DateTime dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * @return the emailAddressDetails
	 */
	public List<EmailAddressEntity> getEmailAddressDetails() {
		return emailAddressDetails;
	}

	/**
	 * @param emailAddressDetails
	 *            the emailAddressDetails to set
	 */
	public void setEmailAddressDetails(List<EmailAddressEntity> emailAddressDetails) {
		this.emailAddressDetails = emailAddressDetails;
	}

	/**
	 * @return the contactNumberDetails
	 */
	public List<ContactNumberEntity> getContactNumberDetails() {
		return contactNumberDetails;
	}

	/**
	 * @param contactNumberDetails
	 *            the contactNumberDetails to set
	 */
	public void setContactNumberDetails(List<ContactNumberEntity> contactNumberDetails) {
		this.contactNumberDetails = contactNumberDetails;
	}

	/**
	 * @return the addressDetails
	 */
	public List<AddressEntity> getAddressDetails() {
		return addressDetails;
	}

	/**
	 * @param addressDetails
	 *            the addressDetails to set
	 */
	public void setAddressDetails(List<AddressEntity> addressDetails) {
		this.addressDetails = addressDetails;
	}
}
