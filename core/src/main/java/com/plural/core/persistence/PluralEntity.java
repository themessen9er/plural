package com.plural.core.persistence;

import java.util.Date;

/**
 * @author Francisco Vargas (themessen9er@gmail.com)
 *
 * The main base representation of an entity in Plural
 */
public interface PluralEntity {
	
	/**
	 * @return The date and time when the entity was created
	 */
	public Date getCreationTimestamp();
	
	/**
	 * @return The date and time when the entity was last updated
	 */
	public Date getUpdateTimestamp();
	
	/**
	 * @return Returns true if the entity is still active. i.e. if it has not been logically deleted
	 */
	public boolean isActive();
	
}
