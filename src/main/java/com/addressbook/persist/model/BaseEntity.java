package com.addressbook.persist.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

/**
 * Base entity for persistence model objects
 * 
 * @author Vrushali
 *
 */
@MappedSuperclass
public class BaseEntity implements Serializable{
	
	/** Version UID for serialization */
	private static final long serialVersionUID = -1320182399185402424L;
	
	/** unique identifier */
	@Id
	@Column(name = "id")
	@Type(type = "uuid-char")
	private UUID id;
	
	/** Record last modified date */
	@Column(name = "last_modified_date")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime lastModifiedDate;
	
	/**
	 * @return the lastModifiedDate
	 */
	public DateTime getLastModifiedDate() {
		return lastModifiedDate;
	}

	/**
	 * @param lastModifiedDate
	 *            the lastModifiedDate to set
	 */
	@PrePersist
	@PreUpdate
	private void setLastModifiedDate() {
		this.lastModifiedDate = DateTime.now();
	}

	/**
	 * @return the id
	 */
	public UUID getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(UUID id) {
		this.id = id;
	}
}
