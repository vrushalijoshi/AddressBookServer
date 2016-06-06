package com.addressbook.persist.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Maintains email address details corresponding to the person entity
 * 
 * @author Vrushali
 *
 */
@Entity
@Table(name = "email_address")
public class EmailAddressEntity extends BaseEntity {

	/** Version UID for serialization */
	private static final long serialVersionUID = -7369370840814288110L;
	
	/** email address */
	@Column(name = "email_id")
	private String emailAddress;
	
	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @param emailAddress
	 *            the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

}
