package com.plural.core.persistence.jpa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.plural.core.persistence.PluralEntity;

/**
 * @author Francisco Vargas (themessen9er@gmail.com)
 *
 * The JPA implementation of {@link PluralEntity}
 */
@MappedSuperclass
public class BaseEntity implements PluralEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="creation_timestamp", insertable = true, updatable = false, nullable = false)
	private Date creationTimestamp;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_timestamp", insertable = true, updatable = true, nullable = false)
	private Date updateTimestamp;
	
	@Column(name="active_entity", insertable = true, updatable = true, nullable = false)
	private boolean active;
	

	protected BaseEntity(){}
	
	@SuppressWarnings("unused")
	@PrePersist
	private void prePersist() {
		Date currentDate = new Date();
		setCreationTimestamp(currentDate);
		setUpdateTimestamp(currentDate);
		setActive(true);
	}
	
	@SuppressWarnings("unused")
	@PreUpdate
	private void preUpdate() {
		setUpdateTimestamp(new Date());
	}
	
	/**
	 * @return The persistence ID
	 */
	public long getID() {
		return this.id;
	}
	
	@Override
	public Date getCreationTimestamp() {
		return this.creationTimestamp;
	}

	@Override
	public Date getUpdateTimestamp() {
		return this.updateTimestamp;
	}

	@Override
	public boolean isActive() {
		return this.active;
	}

	/**
	 * @param creationTimestamp the creationTimestamp to set
	 */
	private void setCreationTimestamp(Date creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}

	/**
	 * @param updateTimestamp the updateTimestamp to set
	 */
	private void setUpdateTimestamp(Date updateTimestamp) {
		this.updateTimestamp = updateTimestamp;
	}

	/**
	 * @param active the active to set
	 */
	private void setActive(boolean active) {
		this.active = active;
	}
	
}
