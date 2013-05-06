package com.plural.core.persistence;

import java.util.Collection;

/**
 * @author Francisco Vargas (themessen9er@gmail.com)
 *
 * The representation of a concept
 */
public interface Concept extends PluralEntity {
	
	/**
	 * @return The {@link Concept}'s name and identifier.
	 */
	public String getName();
	
	/**
	 * @return A more detailed description of the concept.
	 */
	public String getDescription();
	
	/**
	 * @return The list of {@link Resource}s for this Concept
	 */
	public Collection<Resource> getResources();


	/**
	 * Sets the {@link Concept} description
	 * @param description The description
	 */
	public void setDescription(String description);
	
	/**
	 * Adds a {@link Resource} to this {@link Concept}
	 * @param resource A {@link Resource} instance
	 */
	public void addResource(Resource resource);
	
}
