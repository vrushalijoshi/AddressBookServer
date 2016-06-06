package com.addressbook.persist.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Maintains address information related to the person entity
 * 
 * @author Vrushali
 *
 */

@Entity
@Table(name = "address")
public class AddressEntity extends BaseEntity {

	/** Version UID for serialization */
	private static final long serialVersionUID = -1764664487564596584L;

	/** apartment number */
	@Column(name = "apartment_number")
	private String apartmentNumber;

	/** street address */
	@Column(name = "street_address")
	private String streetAddress;

	/** city */
	@Column(name = "city")
	private String city;

	/** state */
	@Column(name = "state")
	private String state;

	/** country */
	@Column(name = "country")
	private String country;

	/** zipcode */
	@Column(name = "zipcode")
	private Integer zipCode;

	/**
	 * @return the apartmentNumber
	 */
	public String getApartmentNumber() {
		return apartmentNumber;
	}

	/**
	 * @param apartmentNumber
	 *            the apartmentNumber to set
	 */
	public void setApartmentNumber(String apartmentNumber) {
		this.apartmentNumber = apartmentNumber;
	}

	/**
	 * @return the streetAddress
	 */
	public String getStreetAddress() {
		return streetAddress;
	}

	/**
	 * @param streetAddress
	 *            the streetAddress to set
	 */
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the zipCode
	 */
	public Integer getZipCode() {
		return zipCode;
	}

	/**
	 * @param zipCode
	 *            the zipCode to set
	 */
	public void setZipCode(Integer zipCode) {
		this.zipCode = zipCode;
	}
}
