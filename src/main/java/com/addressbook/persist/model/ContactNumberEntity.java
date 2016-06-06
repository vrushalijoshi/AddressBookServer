package com.addressbook.persist.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Maintains contact number information corresponding to person entity
 * 
 * @author Vrushali
 *
 */
@Entity
@Table(name = "contact_number")
public class ContactNumberEntity extends BaseEntity {
	
	/** Version UID for serialization */
	private static final long serialVersionUID = -8246786795911798987L;
	
	/** contact number */
	@Column(name = "contact")
	private Long contactNumber;

	/**
	 * @return the contactNumber
	 */
	public Long getContactNumber() {
		return contactNumber;
	}

	/**
	 * @param contactNumber
	 *            the contactNumber to set
	 */
	public void setContactNumber(Long contactNumber) {
		this.contactNumber = contactNumber;
	}
}
